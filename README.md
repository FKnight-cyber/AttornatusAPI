# Projeto AttornatusAPI
Consiste em uma simples API que permite o registro de pessoas e seus dados.

<p align="center">
  <img  src="https://scontent.ffor11-1.fna.fbcdn.net/v/t39.30808-6/291155788_749134162931729_5904111411624050166_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=UhqK0Dd999cAX9Zd6oZ&_nc_ht=scontent.ffor11-1.fna&oh=00_AfDZtIiaJqWps8lEsieehEPjS-wZ4rBoYTvcufUdfXh-sg&oe=63C0C550" height="180px">
</p>
<h1 align="center">
  AttornatusAPI
</h1>
<div align="center">

  <h3>Built With</h3>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" height="30px"/>
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" height="30px"/>  
</div>

# Description

Nesse desafio foi proposto a criação de uma API que permitisse o registro de pessoas, informando o nome e a data de nascimento e endereço.
Devia ser possível atualizar essas informações e também identificar qual o endereço principal.
Também foi proposto a realização de testes, neste caso implementei testes de integração nos controllers da API.

## Features

-   Add person.
-   Add address.
-   Get all registered person, by id, by main address.
-   Get specific person's address.
-   Update person info, address info.

</br>

### Add person

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
POST /people/add
```

#### Request:

| Body            | Type     | Description                     |
| :-------------- | :------- | :------------------------------ |
| `name`          | `string` | **Required**. person name       |
| `birthDate`     | `string` | **Required**. person birthDate  |

####

#### Response:

```json
message: {Person's name} successfully registered!
status: 201
```

####

#

### Add person's address

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
POST /{id}/address/add
```

#### Request:

| Params  | Type     | Description            |
| :------ | :------- | :--------------------- |
| `id`    | `integer`| **Required**. personId |

####

| Body          | Type     | Description               |
| :------------ | :------- | :------------------------ |
| `logradouro`  | `string` | **Required**. logradouro  |
| `cep`         | `string` | **Required**. cep         |
| `houseNumber` | `string` | **Required**. houseNumber |
| `city`        | `string` | **Required**. city        |
| `main`        | `boolean`| **Required**. main        |

#### Response:

```json
message: Added {Person's name}'s new address
status: 201
```

### Get all people

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
GET /people
```

#### Response:

```json
[
  {
    "name": "Fulano",
    "birthDate": "19/02/1996",
    "addresses": [
      {
        "id": 1,
        "logradouro": "Rua tal",
        "cep": "60730012",
        "houseNumber": "1432",
        "city": "Fortaleza"
      }
    ],
    "id": 1
  },
  {
    "name": "Cicrano",
    "birthDate": "13/12/1995",
    "addresses": [
      {
        "id": 2,
        "logradouro": "Rua fulana",
        "cep": "61430112",
        "houseNumber": "765",
        "city": "Jacarecanga"
      }
    ],
    "id": 2
  },
...
]
```

### Get person's info by id

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
GET /people/{id}
```

#### Request:

| Params  | Type     | Description            |
| :------ | :------- | :--------------------- |
| `id`    | `integer`| **Required**. personId |

#### Response:

```json
{
  "name": "Teste",
  "birthDate": "19/11/2000",
  "addresses": [
    {
      "id": 4,
      "logradouro": "Nova Rua desconhecida verdadeira 2 2",
      "cep": "70777074",
      "houseNumber": "1233",
      "city": "Praia em fortaleza"
    },
    {
      "id": 5,
      "logradouro": "Nova Rua conhecida",
      "cep": "67558487",
      "houseNumber": "1234",
      "city": "SP"
    },
    ...
  ],
  "id": 4
}
```
#

### Get persons by name

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
GET /people/name?name={name}
```

#### Request:

| Params  | Type     | Description                  |
| :------ | :------- | :--------------------------  |
| `name`  | `string` | **Required**. person's name  |

#### Response:
If we search for "Fulano" we will recieve an array with all people that has "Fulano" in their full names.
This request can return an empty array.

```json
[
  {
    "name": "Fulano",
    "birthDate": "19/02/1996",
    "addresses": [
      {
        "id": 1,
        "logradouro": "Rua tal",
        "houseNumber": "1432",
        "city": "Fortaleza",
        "main": false,
        "cep": "60730012"
      }
    ],
    "id": 1
  },
  {
    "name": "Fulano de Tal",
    "birthDate": "20/02/1994",
    "addresses": [
      {
        "id": 3,
        "logradouro": "Rua distante de tudo",
        "houseNumber": "567",
        "city": "Nowhere",
        "main": false,
        "cep": "61535111"
      }
    ],
    "id": 3
  }
]
```

#

### Get main address info

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
GET /people/{id}/address/main
```

#### Request:

| Params  | Type     | Description            |
| :------ | :------- | :--------------------- |
| `id`    | `integer`| **Required**. personId | 

####

</br>

#### Response:
It will return status code 404 in case the person hasn't registered
his main address yet.

```json
{
  "name": "Teste",
  "birthDate": "19/11/2000",
  "addresses": [
    {
      "id": 5,
      "logradouro": "Nova Rua conhecida",
      "houseNumber": "1234",
      "city": "SP",
      "main": true,
      "cep": "67558487"
    }
  ],
  "id": 4
}
```

#

### Update person info

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
PUT /people/{personId}
```

#### Request:

| Params  | Type     | Description            |
| :------ | :------- | :--------------------- |
| `id`    | `integer`| **Required**. personId | 

####

| Body    | Type     | Description               |
| :------ | :------- | :------------------------ |
| `name`  | `string` | **Required**. person name |

####

#### Response:

```json
{Person's name}'s data successfully updated!
```
#

### Update address info

```
https://attornatusapi-production.up.railway.app
http://localhost:8080
PUT /people/address/{id}
```

#### Request:

| Params  | Type     | Description              |
| :------ | :------- | :----------------------- |
| `id`    | `integer`| **Required**. addressId  |

####

| Body          | Type     | Description               |
| :------------ | :------- | :------------------------ |
| `logradouro`  | `string` | **Required**. logradouro  |
| `cep`         | `string` | **Required**. cep         |
| `houseNumber` | `string` | **Required**. houseNumber |
| `city`        | `string` | **Required**. city        |
| `main`        | `boolean`| **Required**. main        |

#### Response:

```json
Updated {Person'name}'s address info
```
#

## Environment Variables

None were used

#

## Run

O deploy foi feito utilizando Railway, então é possível testar enviando as requisições para:

https://attornatusapi-production.up.railway.app
#

## Run Locally

Clone the project

```bash
  git clone https://github.com/FKnight-cyber/AttornatusAPI.git
```

Start the server

```bash
  run PersonapiApplication
```

Run integration tests

```bash
 run PersonapiApplicationTests
```
#

## Lessons Learned

Apesar de simples foi um projeto que me fez aprender bastante, até então não tinha desenvolvido nada com Java.
Foi uma experiência nova e gratificante construir uma API com o SpringBoot e também como realizar testes no mesmo.

## Authors

-   Ryan Nicholas a full-stack developer looking for new challenges!.
<br/>

#
