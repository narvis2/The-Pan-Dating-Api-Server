# The-Pan-Dating-Api-Server

## 기존 폴더 구조
```sh
project
├─ ..
├─ src
│ ├─ main  
│ │ ├─ kotlin  
│ │ │ ├─ com.thepan.thepandatingapiserver
│ │ │ │ ├─ common
│ │ │ │ │ ├─ handler
│ │ │ │ │ │ ├─ advice
│ │ │ │ │ │ └─ exception
│ │ │ │ │ │
│ │ │ │ │ ├─ ApiResponse
│ │ │ │ │ └─ etc...
│ │ │ │ │
│ │ │ │ ├─ config
│ │ │ │ │ └─ etc...
│ │ │ │ │
│ │ │ │ ├─ controller  
│ │ │ │ │ ├─ user
│ │ │ │ │ ├─ signup
│ │ │ │ │ ├─ signin
│ │ │ │ │ └─ etc...
│ │ │ │ │
│ │ │ │ ├─ domain  
│ │ │ │ │ ├─ auth
│ │ │ │ │ │ ├─ signin
│ │ │ │ │ │ ├─ signup
│ │ │ │ │ │ │ ├─ dto
│ │ │ │ │ │ │ ├─ service
│ │ │ │ │ │ └─ utils
│ │ │ │ │ │
│ │ │ │ │ ├─ base
│ │ │ │ │ │
│ │ │ │ │ ├─ user
│ │ │ │ │ │ ├─ entity
│ │ │ │ │ │ ├─ repository
│ │ │ │ │ │ └─ service
│ │ │ │ │ │
│ │ │ │ │ └─ etc...
│ │ │ │ │
│ │ ├─└─└─ interceptor 
│ │ │     └─ etc...
│ │ │
│ │ ├─ resource
│ │ │ ├─ static
│ │ │ ├─ templates
└─└─└─└─ application.yml
```