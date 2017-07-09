library(twitteR)
library(qdapRegex)
library(tm)
library(SnowballC)
library(ggplot2)
library(wordcloud)
library(cluster)
library(fpc)

#configuração de acesso ao twitter
api_key<- "iKG8ZdJBtI6TsBgvy2Lb7yfjC"
api_secret<- "lYkN4ihVhoDQe7EUGpGXNIFxfHb4GfkITKLj18jFAB2nNjionz"
access_token<- "211205091-NgeHeNwUXqpUQjQuFTod2JOYaQniEi2ULlvYTI4T"
access_token_secret <- "EV8SzifSErPAmGD6zwrTXFTYx21CnDYNc7jzcxCdVDHKK"
setup_twitter_oauth(api_key, api_secret, access_token, access_token_secret)

#função de limpeza a nível de dataframe
cleanTweets <- function(str){
  #removo possiveis caracteres sujos de possiveis emoticons
  str <- gsub("\\\\[[:graph:]]+"," ",str)
  #removo links url
  str <- rm_url(str, pattern=pastex("@rm_twitter_url", "@rm_url"))
  #removo citações do twitter
  str <- gsub("@[[:alnum:]]+"," ",str)
  #removo RT de retweet
  str <- gsub("RT","",str)
  #removo acentos e caracteres especiais
  str <- gsub("[áàÀÁÂâAãÃäÄ]","a",str)
  str <- gsub("[EéèÈÉêÊëË]","e",str)
  str <- gsub("[IíìÍìîÎïÏ]","i",str)
  str <- gsub("[OóÓòÒôÔÕõöÖ]","o",str)
  str <- gsub("[UúÚùÙûÛüÜ]","u",str)
  str <- gsub("[çÇ]","c",str)
  str <- gsub("[ñÑ]","n",str)
  str <- gsub("[ýÝÿ]","y",str)
  #removo pontuação
  str <- gsub("[\\.\\$\\*\\+\\?\\|\\\\\\^\\[\\]\\{\\}\\(\\)-'´`^~¨%#!*\\:\\;]"," ",str)
  #removo possiveis sobras
  str <- gsub("https"," ",str)
  str <- gsub("desd…","desde",str)
  str <- gsub("olimpiada…","olimpiada",str)
  str <- gsub("k+"," ",str)
  str <- gsub("[❤…]"," ",str)
  #removo caracteres de controle, pontuação, digitos, conjunções de letra unica e caracteres não ascii
  str <- gsub("[[:cntrl:]]"," ",str)
  str <- gsub("[[:punct:]]"," ",str)
  str <- gsub("[[:digit:]]"," ",str)
  str <- gsub("[[:space:]]\\S[[:space:]]"," ",str)
  #str <- rm_non_ascii(str)
  #minimiza caracteres
  str <- tolower(str)
  str
}

#gero um vetor com termos stopwords reconhecidos durante as pesquisas
#e stopwords padrões utilizando o comando stopwords("portuguese") do pacote tm
mystopwords <-c('pae','quanto','acho','…','pq','fc','ca…','estar','eh','ia','r','pr','pe','n','s','ai','wpp','si','nois','pa','ti','mt',' pr…','...','�','❤','de','ne','né','ih','hj','fzd','ue','pá','aq','q','pr...','pra','pro','vc',cleanTweets(stopwords("portuguese")))

# pesquisa "n" tweets no geocode(latitude,longitude,raio) desde a data em since
# por limitações na api gratuita do twitter não pesquisa desde a data em since, e sim os mais recentes
# pode gerar warnings caso o retorno não alcance a quantidade definida em 'n'
tweetList <- searchTwitter("olimpiada",n=10000 ,geocode = "-22.90278,-43.2075,60km" ,since = "2016-09-17")

#gero o dataframe
tweetDF<- do.call("rbind", lapply(tweetList, as.data.frame))
#executo uma limpeza no dataframe com a função cleanTweets, removo os stopwords e os espaços gerados na limpeza
tweetDF$text <- cleanTweets(tweetDF$text)
tweetDF$text <- removeWords(tweetDF$text,mystopwords)
tweetDF$text <- gsub("[[:space:]]+"," ",tweetDF$text)

#stemming atraves do metodo stemDocument do pacote TM
tweetCorpus<-Corpus(VectorSource(tweetDF$text))

#problemas com o stemming em portugues, ao descomentar as linhas abaixo e executar seu codigo a matriz de termos apresenta problemas pois os termos somem
#tweetCorpus.dictonary <- tweetCorpus
#tweetCorpus <- tm_map(tweetCorpus,stemDocument, language = "portuguese")
#tweetCorpus <- tm_map(tweetCorpus,content_transformer(stemCompletion), dictionary = tweetCorpus.dictonary <- tweetCorpus)
#tweetCorpus <- tm_map(tweetCorpus,content_transformer(stemCompletion), dictionary = tweetCorpus.dictonary, type = c("prevalent", "first", "longest",
                                      #                                                                              "none", "random", "shortest"))


#gera matriz de termos que contenham pelo menos 3 caracteres
tweetDTM <- TermDocumentMatrix(tweetCorpus, control = list(wordLengths = c(3, Inf)))
matrix<-as.matrix(tweetDTM)
#gero um dataframe com os termos e frequencias ordenados decrescentemente pelas frequencias
wordfrequency <-sort(rowSums(matrix),decreasing = TRUE)
termsDF<-data.frame(word = names(wordfrequency),freq=wordfrequency)

#calculo quantos termos possuem frequencia igual ou superior a 40
maxterms <- 0
for(f in termsDF$freq){
  if(f>=40)
    maxterms <- maxterms+1
  else
    break()
}
#ao analisar o comando anterior, 
barplot(termsDF[1:maxterms,]$freq, las = 2, names.arg = termsDF[1:maxterms,]$word,
        col ="lightblue", main ="Most frequent words",
        ylab = "Word frequencies")

#apresenta termos associados a 'ouro' segundo uma correlação de 60%
findAssocs(tweetDTM,terms="ouro",corlimit = 0.6)

#mostra os termos com frequencia minima de 20
set.seed(1234)
wordcloud(words = termsDF$word, freq = termsDF$freq, min.freq = 20,
          max.words=200, random.order=FALSE, rot.per=0.35, 
          colors=brewer.pal(8, "Dark2"))

#remoção de termos esparços na matriz de termos e calculo de distancia entre termos
dtmss<-removeSparseTerms(tweetDTM,0.9)
d <- dist(as.matrix(dtmss),method = "euclidean")

#dendograma hierarquico (parte do código com problemas)
#exibição estranha do dendograma
fit <- hclust(d=d, method="ward.D")
plot.new()
plot(fit, hang=-1)
groups <- cutree(fit, k=5)
rect.hclust(fit, k=5, border="red")

#kmeans cluster (parte do código com problemas)
#Error in princomp.default(x, scores = TRUE, cor = ncol(x) > 2) : 
#covariance matrix is not non-negative definite
kfit <- kmeans(d,2)
clusplot(as.matrix(d), kfit$cluster, color=T, shade=T, labels=2, lines=0)   

