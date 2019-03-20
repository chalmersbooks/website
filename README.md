The main folder contains three sub-folders: java, resources and webapp.
java contains all Java code organized into folders to separate responsibilities and concerns. 
resources contains our persistence specification.
webapp contains all xhtml and css files, the xhtml files that are not supposed to be reached by a user that is not logged in are in a folder called authorized and all attempts to reach these pages while not logged in results in getting re-routed to the login page. The template contains code and css that are used on many different pages, such as the header and footer. 
