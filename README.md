# jionic
Java implementation of simple server side task for the ionic framework.

First implementation is ionic push.

## Push

To do a push message to the ionic platform (and hence to APN and GCM), create an ionic object with your application id private key, like so:

```java
Ionic i = new Ionic("xxxx", "xxxx");
i.push(json);
```

The json input should, of course be correct JSON formated, otherwise an exception will arise.
