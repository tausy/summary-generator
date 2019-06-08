# summary-generator
The information age has led to information overload. A simple Google search for - apple renders more than 500 Million results ranging from all walks of life. The problem is compounded by the fact that even if a relevant page is encountered the amount of textual data present in a single page can be quite overwhelming. Further increasing the complexity, if the time is limited, reading the entire document becomes very difficult. Even skimming is difficult as there can be a problem of loss of context. And many a times we find ourselves in need of only the gist or summary of articles that we read. 
This system does exactly this, whatever you are reading online, if it is more than you can handle, simply paste the URL/text into the application, and you will get only few sentences as the summary of the entire article. Simple and easy to use application making your reading experiences on the web a more productive one.


## Technical Details
### Working
The application takes a URL as input, parses the text content in the HTML page and gives the summary of the text. There is a high level of abstraction as far as the working of the application is concerned. There is no plan of using training data for the app to work. Training data is a collection of articles on diverse fields used in key phrase extraction by the algorithm if the algorithm used falls in the category of supervised learning. The end user simply needs to input the URL in the specified URL field. The activity will take the URL and parse the content of the web page using a popular HTML parser, after some time the result should be displayed.
### Programming Language 
The programming language used for development of the application is Java, which is mainly known for its platform independence as the code is compiled into an intermediate ―byte code that can run on any architecture which has Java Virtual Machine or JVM.

## System Architecture
SumGen consists of various modules to achieve the whole task of summarization. The Controller is the core module while there are various other modules to achieve the overall goal along with UI module which acts as the presentation layer and DB module to handle the database related stuff.

