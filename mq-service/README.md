
# MQ-Spring boot Project

Launch a local ***MQ Server*** using Docker
1. **Pull MQ image**

```shell
docker pull ibmcom/mq:latest
```

2. **Create volume**

```shell
docker volume create qmldata
```
3. **start/run container**
```sh
docker run --name=mq 
           --env LICENSE=accept 
           --env MQ_QMGR_NAME=QM1 
           --volume mldata:/mnt/mqm 
           --publish 1414:1414 
           --publish 9443:9443 
           --detach 
           --env MQ_APP_PASSWORD=passw0rd 
           ibmcom/mq:latest
```
4. **Access container**

```sh
docker exec -it mq bash
```  


***Spring Boot***



1. **Create the project and add the server information**   
   *src/main/resources/application.properties*

```properties
ibm.mq.queueManager=QM1
ibm.mq.channel=DEV.ADMIN.SVRCONN
ibm.mq.connName=localhost(1414)
ibm.mq.user=admin
ibm.mq.password=passw0rd
```

2. **Add dependencies**


```xml
 <dependency>
     <groupId>com.fasterxml.jackson.core</groupId>
     <artifactId>jackson-databind</artifactId>
 </dependency>
 <dependency>
     <groupId>com.ibm.mq</groupId>
     <artifactId>mq-jms-spring-boot-starter</artifactId>
     <version>3.0.6</version>
 </dependency>
```

3. **Add @Enable anotation in main class**

```java
@SpringBootApplication
@EnableJms
public class MqspringApplication {

  @Autowired
  private JmsTemplate jmsTemplate;

  public static void main(String[] args) {
      SpringApplication.run(MqspringApplication.class, args);
  }

}
```

4. **Controller Class**

```java
@RestController
public class DemoController {
    private final MessageService messageService;
    @Value("${ibm.mq.connName}")
    private String connName;
    @Autowired
    public DemoController(MessageService messageService) {
        this.messageService = messageService;
    }
    @GetMapping("send")
    public String send(@RequestParam("msg") String msg) {
        return messageService.send(msg);
    }
    @GetMapping("recv")
    public String recv() {
        return messageService.recv();
    }
    
}
```

## References

Here are some resources and documentation links that might be helpful:

- [IBM MQ Official Documentation](https://developer.ibm.com/tutorials/mq-jms-application-development-with-spring-boot/)
- [GitHub Repo for IBM implementation all languages](https://github.com/ibm-messaging/mq-dev-patterns)


