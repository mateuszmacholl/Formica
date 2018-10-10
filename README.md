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
    <br>
  - /comments [GET] [POST] 
    - /{id} [GET] [DELETE]
  <br>
  - /password-reset-tokens [GET] [POST]
    - /{id} [GET] [DELETE]
        - /users [GET]
  <br>
  - /verification-tokens [GET] [POST]
    - /{id} [GET] [DELETE]
        - /users [GET]
  <br>
  - /posts [GET] [POST]
    - /{id} [GET] [DELETE]
        - /comments [GET]
  <br>
  - /tags [GET] [POST]
    - /{id} [GET] [DELETE]
        - /tasks [GET]