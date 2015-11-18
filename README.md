# NanoAndroidSDK
NanoRep API wrapper for Android


Quick Start Guide
======

```
1. git clone https://github.com/nanorepsdk/nano_iOS_SDK/tree/version1.0 to the same folder of your app.
```
```
2. Add reference to NanoAndroidSDK module from your project:
```

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
```
mNanoRep = new NanoRep(this.getApplicationContext(), domain, account, kb, nanoCtx);

// Fetching the default FAQ (usually used when presenting the widget for the first time)
mNanoRep.fetchDefaultFAQWithCompletion(new NRDefaultFAQCompletion() {
	@Override
    public void fetchDefaultFAQ(NRFAQCnf cnf, NRError error) {
    	// use the cnf.getFaqData() for pesenting the default data.
    }
});



```
###Fetching suggestions:
```
mNanoRep.suggestionsForText(text, new NanoRep.NRSuggestionsCompletion() {
            @Override
            public void suggustions(NRSuggestions suggestions, NRError error) {
                if (suggestions != null) {
                    mSuggestions = suggestions.getAnswers();
                    // present the answers.
                }
            }
        });  

```



###Speech recognizer 

```
mNanoRep.startVoiceRecognition(getApplicationContext(), new NRSpeechRecognizerCompletion() {
                @Override
                public void speechReconitionResults(String speechToText) {
                    // use the text for trigger search api
                }
            });

```

## For more information you can use our Documentation:
[NanoRep Documentation](http://htmlpreview.github.io/?https://github.com/nanorepsdk/NanoAndroidSDK/blob/master/JavaDoc/index.html)