# Endpoints
   - /users [GET] [POST]
      - /{id} [GET] [DELETE]
          - /comments [GET]
          - /posts [GET]
          - /notifications [GET]
          - /password-reset-tokens [GET]
          - /verification-tokens [GET]
      - /enabled [PUT]
      - /password [PUT]
      - /password-reset-tokens [POST]
      - /verification-tokens [POST]
  - /notifications [GET]
    - /{id} [GET] [DELETE]
  - /comments [GET] [POST] 
      - /{id} [GET] [DELETE]
         - /content [PATCH]
  - /answers [GET] [POST] 
      - /{id} [GET] [DELETE]
         - /comments [GET]
         - /content [PATCH]
  - /password-reset-tokens [GET] [POST]
      - /{id} [GET] [DELETE]
          - /users [GET]
  - /verification-tokens [GET] [POST]
      - /{id} [GET] [DELETE]
          - /users [GET]
  - /posts [GET] [POST]
      - /{id} [GET] [DELETE]
          - /answers [GET]
          - /tags [PATCH]
          - /title [PATCH]
          - /content [PATCH]
  - /tags [GET] [POST]
      - /{id} [GET] [DELETE]
          - /tasks [GET]
          - /name [PATCH]
  - /auth/login [POST]
