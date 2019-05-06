# Auth Demo App

This app can be used for testing against various types of auth.

- Simple Auth (No Authentication)
- Basic Auth
- OAuth2
- SSL

## Simple (No) Auth

1. Set profile
    ```
    SPRING_PROFILES_ACTIVE=simple-auth
    ```

2. Run the app
3. Call any endpoint

    ```bash
    # create a message
    curl -X POST \
      http://localhost:8080/api/messages \
      -d message-goes-here
      
    # get all messages
    curl -X POST http://localhost:8080/api/messages
    ```

## Basic Auth

1. Set profile
    ```
    SPRING_PROFILES_ACTIVE=basic-auth
    ```

2. Run the app
3. Call endpoints with Basic Auth Header
    ```bash
    # create a message
    curl -X POST \
      http://localhost:8080/api/messages \
      -H 'Authorization: Basic YWRtaW46cGFzc3dvcmQ='
      -d message-goes-here
    
    # get all messages
    curl -X POST \
      http://localhost:8080/api/messages \
      -H 'Authorization: Basic YWRtaW46cGFzc3dvcmQ='
    ```

## OAuth2

1. Set profile
    ```
    SPRING_PROFILES_ACTIVE=oauth2
    ```

2. Run the app
3. Create Token
    ```bash
    curl -X POST \
      http://localhost:8080/oauth/token \
      -H 'Content-Type: application/x-www-form-urlencoded' \
      -H 'Authorization: Basic a2MtY2xpZW50OmtjLXNlY3JldA==' \
      -d 'grant_type=client_credentials&scope=any'
    ```
4. Use Token (substitute `{token}` with value from previous step)
    ```
    # create a message
    curl -X POST \
      http://localhost:8080/api/messages \
      -H 'Authorization: Bearer {token}'
      -d message-goes-here
      
    curl -X GET \
        http://localhost:8080/api/messages \
        -H 'Authorization: Bearer {token}'
    ```
    
## SSL

1. Set profile
    ```
    SPRING_PROFILES_ACTIVE=ssl-auth
    ```
2. Use cert in root of project to call endpoints

Notes:
* _port `8443` is used instead of `8080`_
* _Keystore was generated with this command:_
    ```bash
     keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 
    -keystore keystore.jks -validity 3650
    ```

## Docker

This project is packaged up into a Docker image available for use. Run it with the different profiles mentioned above to test out the various security types.

### Build New Image

```
mvn clean install dockerfile:build

# to push new image
docker login
mvn clean install dockerfile:build dockerfile:push
```

### Run Container

```
# simple auth
docker run -e "SPRING_PROFILES_ACTIVE=simple-auth" -p 8080:8080 msschroe3/http-sink-demo:latest

# basic auth
docker run -e "SPRING_PROFILES_ACTIVE=basic-auth" -p 8080:8080 msschroe3/http-sink-demo:latest

# oauth2
docker run -e "SPRING_PROFILES_ACTIVE=oauth2" -p 8080:8080 msschroe3/http-sink-demo:latest

# ssl auth
docker run -e "SPRING_PROFILES_ACTIVE=ssl-auth" -p 8443:8443 msschroe3/http-sink-demo:latest
```