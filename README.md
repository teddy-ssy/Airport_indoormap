# airport_indoormap
<img src="https://www.navvis.com/hubfs/NavVis_November2018/Images/maps_icon_2.png">

|project start date|6/ 3/ 2019|
|:---:|:---:|

The project focuses on using NLP and other technologies to build a dialog system to analyse and answer the questions that students ask in terms of course units on University of Sydney education system.  

In this project my task is the natural language understand part, specifice the data preprocessing, intent classification and slot filling. about the intent classification and slot filling part, we consider a seq2seq method.   
In terms of the data prrprocessing, we consider the lower case and number word processing.  
We introduce the NER for unit name and degree inforamtion.

## Dependency Packages for Chatbox
|package|version|
|:---:|:---:|
|gensim|3.4.0|
|sickit-learn|0.20.1|
|numpy|1.15.4|
|tensorflow|1.13.1|
|spacy|2.0.16|
|NLTK|3.4.1|

## Architecture
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/architicture.png">
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/architicture2.png">

## feature

### preprocess the input text

it is important to choose the necessary steps and make sure the result is useful for the intent classification and slot filler part. For Cassandra project, The purpose of preprocessing the text is to omit the length of the sentence, leaving the more important part of the sentence to omit the less useful part of the sentence.

First, we need to convert all letters to lower case. In this step, we first unify the text format so that we can follow the string matching and database operations.  
E.g  
The user's input is `"Hi, I want to know the lecturer address of COMP5426."`  
The output of this step is `"hi, i want to know the lecturer address of comp5426."`
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/preprocessing%201.png">

Second, converting numbers words into numbers, 
for the convenience of back slot matching, so in the preprocessing we need to convert numbers words into
numbers.  
E.g
`"comp five four two six"` appears in the user's question,So in the ability of people to understand, we know that users want to express`"comp5426"`, but it is more convenient to query numbers in the database than words,so you need to turn such digital words into numbers in the process of processing.

Third, removing stop words, 
when the user enters a sentence, although thesewords are necessary grammatically, they are not necessary for understanding thesentence. So in order to shorten the length of the sentence as much as possible, we
will remove stop words in this step.
E.g
The user's input is `"hi, i want to know the lecturer address of comp5426."`  
The output is `['hi', 'want', 'know', 'lecturer', 'address', 'comp5426']`  
In this sentence we can identify some components that appear frequently but have no meaning to the whole sentence, such as `“to”` and `“the”`.
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/processing2.png">

To ensure the stop words set can conclude all stop word, It is necessary to select a open source stop words package. For this project, we downloaded the `NLTK stop word package`.

<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/preprocessing3.png">

### Word Embedding

we decided to use an open source word vector library. For the specific course name, the teacher name data is solved by the stream matching method.
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202019-05-21%20%E4%B8%8B%E5%8D%8810.00.12.png">

### intent classification

Understanding user intent is the first step in the next steps. From the user's intent, we then choose which logic to use to get the desired result based on the logical logic of the backend. Based on the intent category and the raw question training set designed above. We decided to implement this model using the seq2seq method, the model is divided into two parts: encoder and decoder.
In encoder part, after review few method ,the Bi-LSTM structure is the most popular one structure used for encoder. At the beginning, it is need define two LSTM cell for forward and backward. We keep the output prob equal to 0.5, This is an effective regularization method that can effectively prevent overfitting.
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/intent1.png">
Then we use the bidirectional_dynamic_rnn to train the input data to the finial
hidden layer state and the output of the structure.
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/intent2.png">
The decoder part for intent classification is more simpler compared with the slot
filler. The output only one value, then it can be return by a argmax layer. The input of
the this layer is the final hidden layer state.
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/intent3.png">

### slot filling 

Same as the intent classification decoder, the input is the state of the last
hidden layer of encoder, the structure of the decoder divide to the training mode and
prediction mode, at first we will define the decoder helper, The helper is actually
how the decoding stage can get the input at the next moment according to the
prediction result. For example, the actual value of the previous moment should be
directly used as the next moment input during the training process. The greedy
method can be used to select the value with the highest probability as the prediction
process. The next moment and so on. So helper can be roughly divided into training
helper and predictive helper.
In the training phase, using the combination of Training Helper + Basic
Decoder, this is generally fixed, of course, you can also define the Helper class
yourself. The prediction phase calls Greedy Embedding Helper + Basic Decoder
combination for greedy decoding.
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/slot1.png">
Currently, Attention mode plus LSTM is one of the effect structure for decoder,
to implement this structure first we need define the Attention machine
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/slot2.png">
And Define the decoder stage to use the LSTMCell and then encapsulate the
attention wrapper
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/slot3.png">
Final apply the dynamic decoder by Tensorflow to produce the output. Call
dynamic_decode for decoding, decoder_outputs is a namedtuple which contains two
items (rnn_outputs, sample_id)
Rnn_output: `[batch_size, decoder_targets_length, vocab_size]`
Sample_id: `[batch_size, decoder_targets_length], tf.int32`
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/slot4.png">
The final output of training:
<img src="https://github.com/teddy-ssy/Task-oriented-chatbot/blob/master/reademe/slot5.png">

### NER

## Roadmap

- [x] **week3** 
    - ~~practice seq2seq model~~
- [x] **week4** 
    - ~~practice n-gram modle~~
- [x] **week5** 
    - ~~practice the slot filling~~
- [x] **week6** 
    - ~~high level of project demo~~
- [x] **week7** 
    - ~~combine the intent classification and word embedding~~
- [x] **week8** 
    - ~~data preprocessing~~
- [x] **week9** 
    - ~~modeify the intent classifcation and slot filling~~
- [x] **week10** 
    - ~~string matching~~
- [x] **week11** 
    - ~~NER~~
- **week12** 
    - modeify the word embedding
- **week13** 
    - test
- **week14** 
    - test
- **week15** 
    - presentation



## Discussion
- [submit issue](https://github.com/teddy-ssy/Airport_indoormap/issues/new)

