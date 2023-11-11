# MealRecipe

<div align="center">
<img width="329" alt="image" src="">
  
> **한-상, 한국인의 밥상** <br/> **개발기간: 2023.09 ~ 2023.11**

</div>


## 배포 주소

> **개발 버전** : [/]() <br>
> **프론트 서버** : [/]() <br>
> **백엔드 서버** : [/]() <br>

## 개발팀 소개

|      민정윤       |          양유정         |                |                                                                                                           
| :------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | 
|   <img width="160px" src="" />    |                      <img width="160px" src="" />    |                   <img width="160px" src=""/>   |
|   [@]([https://github.com](https://github.com/YuneeeM))   |    [@](https://github.com/)  | [@8](https://github.com/)  |
| 부경대학교 컴퓨터공학과 4학년 | 부경대학교 컴퓨터공학과 4학년 | 부경대학교 컴퓨터공학과 4학년 | 부경대학교 컴퓨터공학과 4학년 | 

## 프로젝트 소개

tflite를 활용하여 식료품 목록을 인식하는 객체 인식 기반 레시피를 안내해주는 어플입니다.

더 나아가 맞춤 레시피 추천 기능을 도입하기 위해 TF-IDF 와 협업 필터링을 이용해 사용자 맞춤 레시피를 제공합니다.

#### 작성

REAL

#### 작성

Mashigeta

Our website supports the following component.
1. -
2. -
3. -

## 시작 가이드
### Requirements
For building and running the application you need:

- [Spring boot](https://spring.io/blog/2023/06/22/spring-boot-2-7-13-available-now)
- [Spring boot](https://spring.io/blog/2023/06/22/spring-boot-2-7-13-available-now)
- [Spring boot](https://spring.io/blog/2023/06/22/spring-boot-2-7-13-available-now)


### Installation
``` bash
$ git clone https://github.com/YuneeeM/MealRecipe.git
$ cd MealRecipe
```
#### Backend
```
$ 
$ 
```

#### Frontend
```
$
$
```

---

## Stacks

### Environment
![intellij](https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)             

### Config
![](https://img.shields.io/badge/-?style=for-the-badge&logo=npm&logoColor=white)        

### Development
![flutter](https://img.shields.io/badge/flutter-02569B?style=for-the-badge&logo=Javascript&logoColor=white)
![springboot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=react&logoColor=61DAFB)
![](https://img.shields.io/badge/-?style=for-the-badge&logo=Strapi&logoColor=white)
![](https://img.shields.io/badge/-?style=for-the-badge&logo=Next.js&logoColor=white)


### Communication
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)
![GoogleMeet](https://img.shields.io/badge/GoogleMeet-00897B?style=for-the-badge&logo=Google%20Meet&logoColor=white)

---
## 화면 구성
| 메인 페이지  |  로그인 페이지   |
| :-------------------------------------------: | :------------: |
|  <img width="329" src=""/> |  <img width="329" src="h"/>|  
| 레시피 탐색 페이지   |  레시피 추천 페이지   |  
| <img width="329" src=""/>   |  <img width="329" src=""/>     |

---
## 주요 기능

### ⭐️ 이미지를 활용한 레시피 탐색
- 객체 인식을 통해 식자재를 인식하고 레시피를 추천할 수 있음

### ⭐️ 다양한 식재료 활용 방법 안내 
- 추후 지속적으로 레시피 추가 및 업로드 예정
- 사용자가 직접 레시피를 업로드할 수 있음

### ⭐️ 개인화된 레시피 추천 기능
- 사용자가 원하는 식자재와 알러지 성분을 입력받아 TF-IDF를 고려해 레시피 추천
- 사용자가 남긴 리뷰 평점과 다른 사용자의 리뷰 평점을 고려해 협업필터링 기반 레시피 추천

---
## 아키텍쳐

### 디렉토리 구조
```bash
├── README.md
├── .gitignore
├── build.gradle
├── strapi-backend : 
│   ├── README.md
│   ├── api : db model, api 관련 정보 폴더
│   │   ├── about
│   │   ├── course
│   │   └── lecture
│   ├── config : 서버, 데이터베이스 관련 정보 폴더
│   │   ├── database.js
│   │   ├── env : 배포 환경(NODE_ENV = production) 일 때 설정 정보 폴더
│   │   ├── functions : 프로젝트에서 실행되는 함수 관련 정보 폴더
│   │   └── server.js
│   ├── extensions
│   │   └── users-permissions : 권한 정보
│   ├── favicon.ico
│   ├── package-lock.json
│   ├── package.json
│   └── public
│       ├── robots.txt
│       └── uploads : 강의 별 사진
└── voluntain-app : 프론트엔드
    ├── README.md
    ├── components
    │   ├── CourseCard.js
    │   ├── Footer.js
    │   ├── LectureCards.js
    │   ├── MainBanner.js : 메인 페이지에 있는 남색 배너 컴포넌트, 커뮤니티 이름과 슬로건을 포함.
    │   ├── MainCard.js
    │   ├── MainCookieCard.js
    │   ├── NavigationBar.js : 네비게이션 바 컴포넌트, _app.js에서 공통으로 전체 페이지에 포함됨.
    │   ├── RecentLecture.js
    │   └── useWindowSize.js
    ├── config
    │   └── next.config.js
    ├── lib
    │   ├── context.js
    │   └── ga
    ├── next.config.js
    ├── package-lock.json
    ├── package.json
    ├── pages
    │   ├── _app.js
    │   ├── _document.js
    │   ├── about.js
    │   ├── course
    │   ├── index.js
    │   ├── lecture
    │   ├── newcourse
    │   ├── question.js
    │   └── setting.js
    ├── public
    │   ├── favicon.ico
    │   └── logo_about.png
    └── styles
        └── Home.module.css

```

<!--
```bash
├── README.md : 리드미 파일
│
├── strapi-backend/ : 백엔드
│   ├── api/ : db model, api 관련 정보 폴더
│   │   └── [table 이름] : database table 별로 분리되는 api 폴더 (table 구조, 해당 table 관련 api 정보 저장)
│   │       ├── Config/routes.json : api 설정 파일 (api request에 따른 handler 지정)
│   │       ├── Controllers/ [table 이름].js : api controller 커스텀 파일
│   │       ├── Models : db model 관련 정보 폴더
│   │       │   ├── [table 이름].js : (사용 X) api 커스텀 파일
│   │       │   └── [table 이름].settings.json : model 정보 파일 (field 정보)
│   │       └─── Services/ course.js : (사용 X) api 커스텀 파일
│   │ 
│   ├── config/ : 서버, 데이터베이스 관련 정보 폴더
│   │   ├── Env/production : 배포 환경(NODE_ENV = production) 일 때 설정 정보 폴더
│   │   │   └── database.js : production 환경에서 database 설정 파일
│   │   ├── Functions : 프로젝트에서 실행되는 함수 관련 정보 폴더
│   │   │   │   ├── responses : (사용 X) 커스텀한 응답 저장 폴더
│   │   │   │   ├── bootstrap.js : 어플리케이션 시작 시 실행되는 코드 파일
│   │   │   │   └── cron.js : (사용 X) cron task 관련 파일
│   │   ├── database.js : 기본 개발 환경(NODE_ENV = development)에서 database 설정 파일
│   │   └── server.js : 서버 설정 정보 파일
│   │  
│   ├── extensions/
│   │   └── users-permissions/config/ : 권한 정보
│   │ 
│   └── public/
│       └── uploads/ : 강의 별 사진
│
└── voluntain-app/ : 프론트엔드
    ├── components/
    │   ├── NavigationBar.js : 네비게이션 바 컴포넌트, _app.js에서 공통으로 전체 페이지에 포함됨.
    │   ├── MainBanner.js : 메인 페이지에 있는 남색 배너 컴포넌트, 커뮤니티 이름과 슬로건을 포함.
    │   ├── RecentLecture.js : 사용자가 시청 정보(쿠키)에 따라, 현재/다음 강의를 나타내는 컴포넌트 [호출: MainCookieCard]
    │   ├── MainCookieCard.js : 상위 RecentLecture 컴포넌트에서 전달받은 props를 나타내는 레이아웃 컴포넌트.
    │   ├── MainCard.js : 현재 등록된 course 정보를 백엔드에서 받아서 카드로 나타내는 컴포넌트 [호출: CourseCard]
    │   └── CourseCard.js : 상위 MainCard 컴포넌트에서 전달받은 props를 나타내는 레이아웃 컴포넌트
    │
    ├── config/
    │   └── next.config.js
    │
    ├── lib/
    │   └── ga/
    │   │   └── index.js
    │   └── context.js
    │
    ├── pages/
    │   ├── courses/
    │   │   └── [id].js : 강의 페이지
    │   ├── _app.js : Next.js에서 전체 컴포넌트 구조를 결정, 공통 컴포넌트(navbar, footer)가 선언되도록 customizing 됨.
    │   ├── _document.js : Next.js에서 전체 html 문서의 구조를 결정, lang 속성과 meta tag가 customizing 됨.
    │   ├── about.js : 단체 소개 페이지
    │   ├── index.js : 메인 페이지
    │   ├── question.js : Q&A 페이지
    │   └── setting.js : 쿠키, 구글 애널리틱스 정보 수집 정책 페이지
    │
    ├── public/
    │   ├── favicon.ico : 네비게이션바 이미지
    │   └── logo_about.png : about 페이지 로고 이미지
    │
    └── styles/
        └── Home.module.css

```
-->
