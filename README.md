# Endpoints
  - /users [GET] [POST]
    - /{id} [GET] [DELETE]
        - /comments [GET]
        - /posts [GET]
        - /password-reset-tokens [GET]
        - /verification-tokens [GET]
    - /enabled [PUT]
    - /password [PUT]
    - /password-reset-tokens [POST]
    - /verification-tokens [POST]

    
  - /comments [GET] [POST] 
    - /{id} [GET] [DELETE]


- /password-reset-tokens [GET] [POST]
  - /{id} [GET] [DELETE]
      - /users [GET]


- /verification-tokens [GET] [POST]
  - /{id} [GET] [DELETE]
      - /users [GET]


- /posts [GET] [POST]
  - /{id} [GET] [DELETE]
      - /comments [GET]
    
      
- /tags [GET] [POST]
  - /{id} [GET] [DELETE]
      - /tasks [GET]