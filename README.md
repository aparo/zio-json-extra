# zio-json-extra
This is a collection of extensions to zio-json, very opionated, required to fast migrate circe code to zio-json (mainly [zio-elasticsearch](https://github.com/aparo/zio-elasticsearch)).

It generally targets some functionalities that are missing in zio-json such as:
- Derivation of Enumeration in string
- Help methods to manipulate JSON AST  

There are three modules:
- zio-json-extra that extends zio-json
- zio-json-diffson (WIP) that provides [diffson](https://github.com/gnieh/diffson) support (A scala diff/patch library for Json)
- zio-json-exception that extends Exception managements with classes that can be module-scoped and easy "wireable".

## Derivation Enumeration in String
This capability adds DeriveJsonDecoderEnum/DeriveJsonEncoderEnum that provides enum support based on plain trait/objects
```
  @jsonEnumUpperCase
  sealed trait Status extends EnumUpperCase
  case object Running extends Status
  case object Failure extends Status

  case class Entity(name: String, status: Status)

  implicit val statusDecoder: JsonDecoder[Status] = DeriveJsonDecoderEnum.gen[Status]
  implicit val statusEncoder: JsonEncoder[Status] = DeriveJsonEncoderEnum.gen[Status]
  implicit val entityDecoder: JsonDecoder[Entity] = DeriveJsonDecoder.gen[Entity]
  implicit val entityEncoder: JsonEncoder[Entity] = DeriveJsonEncoder.gen[Entity]

   # sample 

    test("simple enum upper value") {
      import exampleupperenums._
    
      assert(""""RUNNING"""".fromJson[Status])(isRight(equalTo(Running)))
    },
    test("case class with enum upper value") {
      import exampleupperenums._
    
      assert("""{"name":"Sample","status":"FAILURE"}""".fromJson[Entity])(
        isRight(equalTo(Entity(name = "Sample", status = Failure)))
      )
    }

```
More example in [tests](zio-json-extra/src/test/scala/zio/json/ast/DeriveJsonEnumSpec.scala) 
