# Jiwjus's URL Shortening Service

_URL을 입력받아 짧게 줄여주고, Shortened URL을 입력하면 Original URL로 Redirect하는 URL Shortening Service입니다._


<br>

## _Description_

```
Original URL
https://www.google.com/search?q=linux&oq=linux&aqs=chrome.0.69i59l3j69i61j69i60j69i65l3.5615j0j9&sourceid=chrome

becomes...

Shortened URL
http://localhost/b
```

•	URL 입력폼을 제공하고 결과를 출력합니다.

•	shortened URL path는 BASE62 encoding된 8자리 이하의 문자입니다.

•	shortened URL에 대한 redirect 요청에는 original URL Redirect로 응답합니다.

•	동일한 original URL에 대한 shorten 요청은 동일한 shortened URL로 응답합니다.

•	데이터베이스 저장 항목은 original_url, shortened_url, request_count 입니다.

_* request_count : 동일한 URL에 대한 Shortening 요청 수_

<br>

## _How to Install and Run_

> sudo apt install
> `gradle-7.3`, `openjdk-11-jdk`, `mysql-server`

```

$ git clone https://github.com/jiwoo-jus/URL-Shortening-Service.git

$ cd URL-Shortening-Service

$ gradle wrap

$ ./gradlew build or gradlew build

$ cd build/libs

$ java -jar URLShorteningService-0.0.1-SNAPSHOT.jar
```

<br>

## _Preview_

![preview_image](https://user-images.githubusercontent.com/71170591/146491517-07c1ea43-5f40-4937-a0d1-ec3e82b65c31.png)

![image](https://user-images.githubusercontent.com/71170591/146493824-b0d115c5-8f6e-4cfa-9104-f76f73eb0a92.png)
