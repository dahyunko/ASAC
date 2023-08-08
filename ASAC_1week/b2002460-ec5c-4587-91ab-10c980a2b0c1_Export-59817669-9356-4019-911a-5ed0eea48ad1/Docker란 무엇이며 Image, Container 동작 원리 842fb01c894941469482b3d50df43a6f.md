# Docker란 무엇이며 Image, Container 동작 원리

## Docker

---

### **Docker란?**

리눅스 **컨테이너 기반**으로 하는 **오픈소스 가상화 플랫폼**

**Docker Engine** : 컨테이너를 생성하고 관리하는 주체

![Untitled](Docker%E1%84%85%E1%85%A1%E1%86%AB%20%E1%84%86%E1%85%AE%E1%84%8B%E1%85%A5%E1%86%BA%E1%84%8B%E1%85%B5%E1%84%86%E1%85%A7%20Image,%20Container%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%20%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20842fb01c894941469482b3d50df43a6f/Untitled.png)

### 도커 **사용 이유**

**environment disparity** (개발환경에 맞지않는 상태) **해결**

ex) window 실행환경에 linux서버에 프로그램을 올리는 데 안 올라가는 것

→ 도커를 통해서 다른 머신에서도 같은 환경을 구현할 수 있게 해줌

### **가상화 방식**

**가상화** : 단일 하드웨어 시스템에서 여러 운영체제가 동시에 실행될 수 있도록 하는 것

- 기존 OS 가상화 방식 : 한 서버의 여러 OS를 가상화하여 사용하는 것
- 컨테이너 : OS레벨의 가상화로 프로세스를 격리시켜 동작하는 방식

### **VM 가상화 플랫폼 vs Docker 가상화 플랫폼**

![Untitled](Docker%E1%84%85%E1%85%A1%E1%86%AB%20%E1%84%86%E1%85%AE%E1%84%8B%E1%85%A5%E1%86%BA%E1%84%8B%E1%85%B5%E1%84%86%E1%85%A7%20Image,%20Container%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%20%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20842fb01c894941469482b3d50df43a6f/Untitled%201.png)

- **VM(Virtual Machine) : 하드웨어 수준에서 가상화**
    - **Hypervisor**를 이용해 여러개의 운영체제(Guest OS)를 하나의 호스트에서 생성해서 사용하는 방식
    - 여러 개의 운영체제는 가상머신이라는 단위로 구별
    - 가상 머신에는 Ubuntu, CentOS 등의 운영체제가 설치
    - ex) VirtualBox, VMware
    - 단점
        - **Hypervisor**를  반드시 거치는 작업은 **일반 호스트OS**에 비해 → 성능 손실이 큼
        - **Guest OS**를 사용하기 위한 라이브러리, 커널 등을 포함 → 배포할 때 용량이 큼
            
            ex) 수 기가 바이트에 달하는 가상 머신 이미지를 애플리케이션으로 배포하는 데에는 부담
            

- **도커 컨테이너 : 애플리케이션 계층에서 가상화**
    - 가상화된 공간을 생성하기 위해 리눅스 자체 기능을 사용하여 프로세스 단위로 격리 환경을 만드므로 → 성능 손실 없음
    - 가상머신과 달리 커널을 공유해서 사용하므로, 컨테이너에는 라이브러리 및 실행 파일 만 있음 → 용량이 작음
    - 따라서 컨테이너를 이미지로 만들 때
        
        → 배포하는 시간이 VM에 비해 빠르며, 사용할 때의 성능 손실이 거의 없음
        
    - 장점
        - 한 서버에 여러개의 컨테이너 가질 수 있음
        
        → 새로운 서비스를 만들 때마다 새로운 서버를 설정할 필요 없음
        
        → 원할 때마다 도커를 통해서 새로운 환경 생성 가능 
        
        → 하나의 같은 서버에서 각기 다른 환경의 컨테이너 설정 가능
        
    - **결론**
        1. 원하는 개발 환경을 파일에 저장하면, docker는 어떤 머신이든 해당 환경을 시뮬레이션 해줌
        2. 이러한 환경들은 각기 독립적으로 존재하기 때문에, 원하는 무슨 환경이든 모듈식으로 관리 가능(여러 서버를 살 필요없음)

### Docker Image, Container 동작 원리

- **Docker Image**
    
    : 서비스 운영에 필요한 서버 프로그램, 소스코드 및 라이브러리, 컴파일된 실행 파일을 묶는 형태 → 컨테이너를 실행할 수 있는 실행파일, 설정 값 들을 가지고 있는 것
    
    - 특징
        - 불변성 : 소스 코드, 라이브러리, 종속성, 도구 및 응용 프로그램을 실행하는데 필요한 기타 파일을 포함하는 불변(변경 불가) 파일
        - 일관성 : 읽기 전용(스냅샷), 특정 시점의 애플리케이션과 가상 환경
            
            → 개발자가 안정적이고 균일한 조건에서 sw를 테스트하고 실험할 수 있음
            
    - **Image 생성 과정**
        1. Ubuntu 이미지를 만들기 위해 Layer A,B,C 가 들어감 → Ubuntu
            
            ** 레이어(Layer) : 기존 이미지에 추가적인 파일이 필요할 때 다시 다운로드 받는 방법이 아닌 해당 파일을 추가하기 위한 개념
            
        2. Nginx 이미지를 만들 경우에는 Ubuntu 이미지를 베이스 이미지로 사용해서 베이스 이미지에 Nginx를 더하면 됨 → Ubuntu + Nginx
        3. Web App 이미지를 만들 경우 → 이미 만들어진 Nginx 베이스 이미지에 Web App 올려 이미지 생성 → Ubuntu + Nginx + Web App\
            
            ![Untitled](Docker%E1%84%85%E1%85%A1%E1%86%AB%20%E1%84%86%E1%85%AE%E1%84%8B%E1%85%A5%E1%86%BA%E1%84%8B%E1%85%B5%E1%84%86%E1%85%A7%20Image,%20Container%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%20%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20842fb01c894941469482b3d50df43a6f/Untitled%202.png)
            
        - Docker image creation : `docker build -t=myimage .`
    - **Docker File**
        
        : 이미지 생성 출발점으로 이미지를 구성하기 위한 명령어들을 작성하여 이미지를 구성할 수 있음 
        
        → Docker File을 읽을 수 있다면 해당 이미지의 구성을 알 수 있음
        
        ```bash
        ###Docker File 예시
        FROM jdk8:latest
            
        WORKDIR /app
        
        RUN mkdir /app/nexus-2.14.9-01
        RUN mkdir /app/sonatype-work
        RUN yum -y install httpd
        
        ENV JAVA_HOME /usr/local/jdk1.8.0_181
        ENV PATH=$JAVA_HOME/bin:$PATH
        ENV CLASSPATH=.
        
        EXPOSE 3411 
        ADD run.sh /app/
        ```
        

- **Docker Container**
    
    :  사용자가 기본 시스템에서 애플리케이션을 분리할 수 있는 가상화된 런타임 환경
    
    → 응용프로그램을 빠르고 쉽게 시작할 수 있는 portable units, Image를 실행한 상태
    
    - 특징
        - 컨테이너 내부에서 실행되는 컴퓨팅 환경의 표준화
            
            : 응용 프로그램이 동일한 환경에서 작동하도록 할 뿐 아니라 다른 사람과의 공유도  단순화함
            
        - 자율적(autonomous) : strong isolation 제공하며 서로 방해하지 않음 → 격리
    
    ![Untitled](Docker%E1%84%85%E1%85%A1%E1%86%AB%20%E1%84%86%E1%85%AE%E1%84%8B%E1%85%A5%E1%86%BA%E1%84%8B%E1%85%B5%E1%84%86%E1%85%A7%20Image,%20Container%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%20%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20842fb01c894941469482b3d50df43a6f/Untitled%203.png)
    
    - Docker container creation : `docker run -d --name mycontainer -p 8080:8080 myimage`

### Docker Image vs Container

**Image**

---

Container Blueprint(설계도)

Container 없이 존재 가능

변형 불가(read only)

컴퓨팅 자원을 필요로 하지 않음

공개/ 비공개 Registry 플랫폼으로 공유(ex. Docker hub)

단 한번만 생성

**Container**

---

Docker Image Instance

Image를 실행해야 존재 가능

쓰기 가능

실행을 위한 컴퓨팅 자원 필요

이미 실행 중인 개체는 공유 필요 없음

같은 이미지로 부터 다수의 컨테이너 생성

### **Dockerfile --(Build)--> Image --(Create)--> Container**

- Dockerfile : 도커 이미지를 빌드하는 방법을 정의하는 스크립트
    - 환경 정보를 저장하는 파일으로 컨테이너의 구동에 필요한 정보 작성
    - Docker file을 빌드해서 이미지 생성
- Image : 특정 환경에 대한 정보가 변하지 않고 저장되는 정적 형태 파일
- Container : 이미지를 동적인 형태로 변경
    - 컨테이너는 어플리케이션을 실행할 격리된 환경
    - 컨테이너를 생성하는 것은 다른 환경과 분리시킨 환경을 만드는 것
    - 컨테이너가 구동된다는 것은 우리가 필요로 하는 OS와 특정 환경이 경량 가상화로 구현된다는 것

- 자료 출처
    - [https://khj93.tistory.com/entry/Docker-Docker-개념](https://khj93.tistory.com/entry/Docker-Docker-%EA%B0%9C%EB%85%90)
    - [https://ebbnflow.tistory.com/200#:~:text=즉%2C 개발 환경이 맞지,을 environment disparity라고 합니다](https://ebbnflow.tistory.com/200#:~:text=%EC%A6%89%2C%20%EA%B0%9C%EB%B0%9C%20%ED%99%98%EA%B2%BD%EC%9D%B4%20%EB%A7%9E%EC%A7%80,%EC%9D%84%20environment%20disparity%EB%9D%BC%EA%B3%A0%20%ED%95%A9%EB%8B%88%EB%8B%A4).
    - [https://sunrise-min.tistory.com/entry/Docker-Container와-Image란-무엇인가](https://sunrise-min.tistory.com/entry/Docker-Container%EC%99%80-Image%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80)
    - 노마드 코더: [https://www.youtube.com/watch?v=chnCcGCTyBg](https://www.youtube.com/watch?v=chnCcGCTyBg)
    - 드림코딩 : [https://www.youtube.com/watch?v=LXJhA3VWXFA](https://www.youtube.com/watch?v=LXJhA3VWXFA)