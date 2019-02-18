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

`
{
 "title":"Title",
 "message":"This is a dynamically created dialog and you are required to respect the genius behind it :*",
 "header_image":"https://images.unsplash.com/photo-1472213984618-c79aaec7fef0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
 "icon_image":"https://slashg.xyz/img/slashg_logo_64.png",
 "is_dismissable":false,
 "buttons":[
     {
         "text":"Button 0",
         "type":"primary|secondary|neutral",
         "action":"dismiss|url",
         "is_enabled":true
     },
     {
         "text":"Button 1",
         "type":"primary|secondary|neutral",
         "action":"dismiss|url",
         "is_enabled":true
     }
     ]
}
`