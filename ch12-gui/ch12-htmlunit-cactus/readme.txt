$Id: $

If you cannot compile, it is probably because a jar file cannot be found. 

Run Maven to get all dependencies from the internet, for example:

mvn clean install

If you are using Eclipse make sure M2_REPO is defined to point to the local Maven repository.

Tested with:

Apache Maven 2.2.1 (r801777; 2009-08-06 12:16:01-0700)
Java version: 1.6.0_18
Java home: C:\Program Files\Java\jdk1.6.0_18\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows vista" version: "6.0" arch: "amd64" Family: "windows"


