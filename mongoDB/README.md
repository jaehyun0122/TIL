## Mongo DB에 Json데이터 저장

유니티에서 캐릭터 정보를 json형식으로 제공 => spring boot로 post요청 => MongoDB에 데이터 저장

application.yml

```
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/test // mongodb://주소:포트번호/db이름
```

입력 데이터

```
{

    "_id": "jjh122@naver.com",

    "settingsName": "MaleSettings",

    "selectedElements": {

        "Hair": 0,

        "Beard": -1,

        "Hat": -1,

    },

    "blendshapes": [

        {

            "blendshapeName": "Fat",

            "type": 0,

            "group": 0,

            "value": 0.0

        },

        {

            "blendshapeName": "Muscles",

            "type": 1,

            "group": 0,

            "value": 0.0

        },

    ],

    "minLod": 0,

    "maxLod": 3,

    "skinColor": [

        0.8679245114326477,

        0.5573326945304871,

        0.4544321298599243,

        1.0

    ],

    "height": -0.10654502362012863,

    "headSize": 0.0

}
```



1. Collection & 클래스 생성

   ```
   package com.example.mongodbwithreact.api.dto;

   import lombok.Getter;
   import lombok.Setter;
   import org.springframework.data.mongodb.core.mapping.Document;

   import javax.persistence.Id;
   import java.util.ArrayList;
   import java.util.List;

   @Getter
   @Setter
   @Document(collation = "user")
   public class User {
       @Id
       private String _id;

       private String settingsName;
       private List<Blendshapes> blendshapes = new ArrayList<>();
       private SelectedElements selectedElements;
       private int MinLod;
       private int MaxLod;
       private List<Double> SkinColor = new ArrayList<>();
       private double Height;
       private float HeadSize;

       @Override
       public String toString() {
           return "User{" +
                   "_id='" + _id + '\'' +
                   ", settingsName='" + settingsName + '\'' +
                   ", blendshapes=" + blendshapes +
                   ", selectedElements=" + selectedElements +
                   ", MinLod=" + MinLod +
                   ", MaxLod=" + MaxLod +
                   ", SkinColor=" + SkinColor +
                   ", Height=" + Height +
                   ", HeadSize=" + HeadSize +
                   '}';
       }
   }

   ```

   ```
   package com.example.mongodbwithreact.api.dto;

   import lombok.Getter;
   import lombok.Setter;
      @Getter
      @Setter
      public class Blendshapes {

      private String blendshapeName;
      private int type;
      private int group;
      private float value;

      }
   ```



```

    package com.example.mongodbwithreact.api.dto;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter
    @Setter
       public class SelectedElements {
    	private int Hair;
       private int Beard;
       private int Hat;
       private int Shirt;
       private int Pants;
       private int Shoes;
       private int Accessory;
       private int Item1;
       }
 ```
2. repository에서 MongoRepostitory 상속

   ```
   package com.example.mongodbwithreact.api.repo;
   import com.example.mongodbwithreact.api.dto.User;
   import org.springframework.data.mongodb.repository.MongoRepository;

   public interface UserRepo extends MongoRepository<User, String> {
     }
   ```

3. controller에서 데이터를 받고 db에 저장수행

   ```

       @Autowired
       private UserRepo userRepo;
    
       @PostMapping("")
       public ResponseEntity<User> insertCharInfo(HttpServletRequest req, HttpServletResponse res, @RequestBody User user){
           System.out.println("user : " + user);
           userRepo.save(user);
           return new ResponseEntity<>(user, HttpStatus.OK);
       }

   ```
4. Mongo DB Compass 확인

   ```
_id
"jjh122@naver.com"
settingsName
"MaleSettings"

blendshapes
Array

selectedElements
Object
MinLod
0
MaxLod
3

SkinColor
Array
Height
-0.10654502362012863
HeadSize
0
_class
"com.example.mongodbwithreact.api.dto.User"

   ```
