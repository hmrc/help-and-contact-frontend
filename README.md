# Help and Contact Frontend

This repository contains the Help and Contact section of the Business tax account.

## Running the app

### Start Dependencies

```
sm2 --start BTA_MIN
```
### Running the application locally
```
sm2 --stop HELP_AND_CONTACT_FRONTEND
./run.sh
```
Note: It runs on port **9733** by default.
### Running the test suite

#### Unit tests:
```
sbt test
```

#### Integration tests:
```
sbt it:test
```

#### All tests with coverage report:
```
./run-all-tests.sh
```

## Related services:

- [business-tax-account (BTA)](https://github.com/hmrc/business-tax-account)
- [BTA acceptance tests](https://github.com/hmrc/business-tax-account-acceptance-tests) - UI Tests

### License


This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
