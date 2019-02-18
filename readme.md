# Dynalog

## Dynalog = Dynamic Dialog
 
 [ ![Download](https://api.bintray.com/packages/slashg0/maven/dynalog/images/download.svg) ](https://bintray.com/slashg0/maven/dynalog/_latestVersion)
 
It's a simple library to init a beautiful dialog with the following components:

* Header Image
* Title
* Message (markdown supported)
* Buttons (horizontally spread, no upper limit)
* Customisable colour theme 
* Cancelable parameter

The entire dialog can be configured with a simple JSON object:

```javascript
{
 "title":"Title",
 "message":"This is a dynamically created dialog and you are required to respect the genius behind it :*",
 "header_image":"<image_url>",
 "is_dismissable":false,
 "buttons":[
     {
         "text":"Leftmost button",
         "type":"coloured|default|borderless",
         "action":"dismiss|redirect",
         "is_enabled":true
     },
     {
         "text":"Rightmost button",
         "type":"coloured|default|borderless",
         "action":"dismiss|redirect",
         "is_enabled":true
     }
     ]
}
```