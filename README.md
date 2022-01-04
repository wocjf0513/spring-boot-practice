# <span style="color:green"> React(Front) + Spring Boot(Back)</span>
#### 작업 하기 앞서, React와 Spring Boot는 각각 다른 서버에서 돌아가기 때문에, 연동시 Front에 문제가 생기면 Back까지 문제가 생길 수 있다.
#### 하지만, Spring Boot에서 React와 같은 view를 제공하기로 했다.

</br>

## <span style="color:green"> 1. react + spring boot 환경 구성 및 연동 </span>
</br>

> 먼저 연동하기 위한 환경을 구성하기 위해 react를 설치해야 한다.
</br>$ npx create-react-app frontend 명령어를 통해 react 를 설치할 수 있는데, 이 파일은 spring -boot 프로젝트 안에 넣어줘야 한다. 
>> 명령어를 실행하기 위해 npm을 설치해야 한다. npx는 npm의 추가 도구이다. (5.2.0버젼부터 추가 도구를 제공한다.)

</br>

> npm을 설치하기 위해서는 Node.js를 설치해야한다.
>> Node.js 는 구글링을 통해 쉽게 설치할 수 있다.

</br>

> react를 설치하게 되면, 리액트와 스프링부트가 다른 포트에서 실행되기 때문에 CORS문제가 발생하는데, 이를 위해서 react의 proxy를 잡아줘야한다.
>><span style="color:green">[Package.json]</span>
</br>프록시란? 서버와 클라이언트 사이에 중계기로서 대리로 통신을 수행하는 것을 가리킨다.
```json
{
  "name": "fronted",
  "version": "0.1.0",
  "private": true,
  "dependencies": {
    "@testing-library/jest-dom": "^5.16.1",
    "@testing-library/react": "^12.1.2",
    "@testing-library/user-event": "^13.5.0",
    "react": "^17.0.2",
    "react-dom": "^17.0.2",
    "react-scripts": "5.0.0",
    "web-vitals": "^2.1.2"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  },

  "proxy":"http://localhost:8080",

  "eslintConfig": {
    "extends": [
      "react-app",
      "react-app/jest"
    ]
  },
  "browserslist": {
    "production": [
      ">0.2%",
      "not dead",
      "not op_mini all"
    ],
    "development": [
      "last 1 chrome version",
      "last 1 firefox version",
      "last 1 safari version"
    ]
  }
}
```
</br>

> 프록시를 잡은 이후에 예제를 이용해 Rest.Api를 구현해보았다.
>> <span style="color:green">[App.js]</span>
```js
import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';

function App () {
const [message, setMessage] = useState("");
useEffect(() => {
fetch('/api/hello')
.then(response => response.text())
.then(message => {
setMessage(message);
});
},[])
return (
<div className="App">
<header className="App-header">
<img src={logo} className="App-logo" alt="logo"/>
<h1 className="App-title">{message}</h1>
</header>
<p className="App-intro">
To get started, edit <code>src/App.js</code> and save to reload.
</p>
</div>
)
}
export default App;
```

</br>

> 다음과 같이 변경해주면 둘 다 서버를 켰을 때, react에 spring boot가 적용됨을 볼 수 있다.따로 서버를 켜주는 일은 불편하니 스프링부트가 maven을 통해 빌드 될 때, npm이 실행되게 함으로서 연동할 수 있다.
>><span style="color:green">[pom.xml]</span>
</br>  이때, plugin은 최신 버젼, npm과 node.js는 자신의 로컬 버젼과 같아야 한다.
</br> https://github.com/eirslett/frontend-maven-plugin  최신 버젼은 다음을 참고하자.
```xml
<plugin>
				<groupId>com.github.eirslett</groupId>
				<artifactId>frontend-maven-plugin</artifactId>
				<version>1.12.1</version>
				<configuration>
				<workingDirectory>frontend</workingDirectory>
				<installDirectory>target</installDirectory>
				</configuration>
				<executions>
				<execution>
				<id>install node and npm</id>
				<goals>
				<goal>install-node-and-npm</goal>
				</goals>
				<configuration>
				<nodeVersion>v16.13.1</nodeVersion>
				<npmVersion>8.1.2</npmVersion>
				</configuration>
				</execution>
				<execution>
				<id>npm install</id>
				<goals>
				<goal>npm</goal>
				</goals>
				<configuration>
				<arguments>install</arguments>
				</configuration>
				</execution>
				<execution>
				<id>npm run build</id>
				<goals>
				<goal>npm</goal>
				</goals>
				<configuration>
				<arguments>run build</arguments>
				</configuration>
				</execution>
				</executions>
			</plugin>
```

</br>

> 이후 ./mvnw clean install 을 통해 재빌드를 하면 spring boot 서버를 키는 것만으로 maven에서 react와 spring boot를 같이 패키징을 해 실행 할 수 있다.

</br>

> 추가로 plugin을 다음과 같이 추가한다면, react에 있는 build 폴더를 spring boot project에 포함시킬 수 있고, frontend 와 backend 나눠서 작업이 가능해진다.
>><span style="color:green">[pom.xml]</span>
```xml
<plugin>
<artifactId>maven-antrun-plugin</artifactId>
<executions>
<execution>
<phase>generate-resources</phase>
<configuration>
<target>
<copy todir="${project.build.directory}/classes/public">
<fileset dir="${project.basedir}/frontend/build"/>
</copy>
</target>
</configuration>
<goals>
<goal>run</goal>
</goals>
</execution>
</executions>
</plugin>
```









