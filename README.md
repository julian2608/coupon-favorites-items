## Coupon challenge - Julian Gutierrez

### Pre requisites for execute project

- [docker] - Used for packaging
- [docker-compose] Used for management multiple images of the project
- [git] - Used for clone base code

### Project  run instructions
- Clone this repository `git clone git@github.com:julian2608/coupon-favorites-items.git`
> Note: This step require config ssh key github and your user github having grant permissions in this repo
- Get located in path project cloned.
- Execute `docker-compose up`

## Documentation
### Token
> #### <span style="color:yellow">POST</span>`https://dev-jfjtqh787zax8awu.us.auth0.com/oauth/token`
>> #### BODY 
>>```json
>>{
>>    "client_id": "{{APP_CLIENT_AUTH0}}",
>>    "client_secret": "{{CLIENT_SECRET_AUTH0}}",
>>    "audience": "https://dev-jfjtqh787zax8awu.us.auth0.com/api/v2/",
>>    "grant_type": "client_credentials"
>>}
>>```

### Maximized coupon
> #### <span style="color:yellow">POST</span>`/coupon`
> Note: 
>> #### BODY
>>```json
>>{
>>    "amount": 100.00,
>>    "item_ids": ["MCO123456", "MCO9876554"]
>>}
>>```
>> #### RESPONSE
>>```json
>>{
>>  "item_ids": ["MCO123456","MCO9876554","MCO455954"],
>>  "total": 95.0
>>}
>>```


### Stats favorites

> #### <span style="color:green">GET</span>`/coupon/stats?maxTop=10`
> * Path param ***maxTop*** is optional, default value is **5**
>> #### RESPONSE
>>```json
>>[
>>   {
>>        "id": "MCO123456",
>>        "quantity": 50
>>    },
>>   {
>>        "id": "MCO9876554",
>>        "quantity": 40
>>    },
>>    {
>>        "id": "MCO455954",
>>        "quantity": 30
>>    },
>>    {
>>        "id": "MCO987344",
>>        "quantity": 20
>>    },
>>    {
>>        "id": "MCO835554",
>>        "quantity": 10
>>    }
>>]
>>```

