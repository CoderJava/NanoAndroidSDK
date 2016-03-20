# NanoAndroidSDK
NanoRep API wrapper for Android


Quick Start Guide
======


1. git clone https://github.com/nanorepsdk/NanoAndroidSDK to the same folder of your app.

2. Add reference to NanoAndroidSDK module from your project:


#####Select _`settings.gradle`_ and add:

```
include ':NanoAndroidSDK'
project(':NanoAndroidSDK').projectDir=new File('../NanoAndroidSDK')
```

#####Select _`build.gradle(Module: app)`_ and add:
```
dependencies {
    ...
    ...
    compile project(':NanoAndroidSDK')
    ...
}
```

##Using NanoRep SDK:

#####Init nanorep:

```

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NanoRep.initializeNanorep(getApplicationContext(), "MobileSDK_Test" ,"my.nanorep.com");
    }
}

``` 

#####Fetch NanoRep configuration:
```
mNanoRep = new NanoRep(kb, nanoCtx);

// Fetching the default FAQ (usually used when presenting the widget for the first time)
mNanorep.fetchDefaultFAQWithCompletion(new NRDefaultFAQCompletion() {
                @Override
                public void fetchDefaultFAQ(NRFAQCnf cnf, NRError error) {
                    if (error != null) {
                        Log.e(error.getDomain(), error.getDescription());
                    } else {
                        if (cnf.getFaqData() != null) {
                            NRFAQGroupItem group = cnf.getFaqData().getGroups().get(0);
                            NRFAQAnswerItem answer = group.getAnswers().get(0);
                            // call answer by - answer.getObjectId();
                        }
                    }
                }
            });

```

#####Fetch FAQ answer:
```
mNanorep.answerWithId(answer.getObjectId(), new NRFAQAnswerCompletion() {
                                @Override
                                public void fetchAnswer(NRFAQAnswer answer, NRError error) {
                                    // present the answer
                                }
                            });
```
#####Fetching suggestions:
```
mNanoRep.suggestionsForText(text, new NanoRep.NRSuggestionsCompletion() {
            @Override
            public void suggustions(NRSuggestions suggestions, NRError error) {
                if (suggestions != null) {
                    mSuggestions = suggestions.getSuggestions();
                    // present the answers, or use the searchText api
                }
            }
        });  

```

#####Search text:
```
mNanorep.searchText("some answer from suggestions above", new NanoRep.NRSearchCompletion() {
                @Override
                public void searchResponse(NRSearchResponse response, NRError error) {
                    Log.d("test", response.getAnswerList().toString());
                }
            });
```

#####Channeling:
NRSearchResonse includes AnswerList and each answer includes channeling:

```
mNanoRep.searchText("some text", new NanoRep.NRSearchCompletion() {
                @Override
                public void searchResponse(NRSearchResponse response, NRError error) {
                    ArrayList<NRChanneling> channels = 					 response.getAnswerList().get(0).getChanneling();
                    for (NRChanneling channel: channels) {
                        switch (channel.getType()) {
                            case PhoneNumber:
                                // use channel as NRChannelingPhoneNumber
                                break;
                            case OpenCustomURL:
                                // use channel as NRChannelingOpenCustomURL
                                break;
                            case ChatForm:
                                // use channel as NRChannelingChatForm
                                break;
                            case ContactForm:
                                // use channel as NRChannelingContactForm
                                break;
                            case CustomScript:
                                // use channel as NRChannelingCustomScript
                                break;
                        }
                    }
                }
            });
```

You can present the channel by the parameters which the NRChannel subclass contains:

1. NRChannelingChatForm
2. NRChannelingContactForm
3. NRChannelingCustomScript
4. NRChannelingOpenCustomURL
5. NRChannelingPhoneNumber


###Speech recognizer 

```
mNanoRep.startVoiceRecognition(new NRSpeechRecognizerCompletion() {
                @Override
                public void speechReconitionResults(String speechToText) {
                    // use the text for trigger search api
                }
            });

```

## For more information you can use our Documentation:
[NanoRep Documentation](http://htmlpreview.github.io/?https://github.com/nanorepsdk/NanoAndroidSDK/blob/master/JavaDoc/index.html)