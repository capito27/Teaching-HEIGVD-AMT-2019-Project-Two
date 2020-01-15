## Project-One

### Testing Strategy
We implemented 3 types of tests : 

- Unit Tests
- Integration Tests
- Mock Tests

#### Unit tests
We used unit tests mainly to test our models.

Unit testing is quite adapted to testing boilerplate code, as it's behaviour and implementation details are quite unlikely to ever change, we can test at an implementation level, rather than having to test at an interraction level.

We used junit5 to do our unit testing, which is a fine unit testing framework, not cumbursome to interract and runs quickly

#### Integration tests

We used integration testing to integration layer.

Integration testing is more adapted to testing that layer than unit testing and mock testing, indeed, we want to be able to test exactly how certain calls will affect the underlying datasource, and while we don't exactly care about exact implementation details, we care that if we call methodA, then methodB's behaviour can be expected and has been affected by the first method call.

A simple example of that, is that if we delete some data, we don't want the deleted data to be fetchable from the datasource.

Thus, integration testing is the most suitable to test the integration layer.

We used the `Arquillian` suite to do our integration testing.

Its main advantage, in my opinion, is the automatic rollback of datasource edition, which allows for some pretty destructive tests, without affecting the underlying data afterward.

While a powerful piece of software, its setup is extremely cumbersome, and quite fragile.

Also, its tests are quite dificult to debug and take an exceedingly long time to run (20+ seconds compared to less than one).

As such, we hope to never have to interract with `Arquillian` in the future.

#### Mock testing

We used mock testing for our controllers / presentation layer.

Indeed, mock testing is the most appropriate type of testing for the job. Unlike unit or integration testing, Mock testing can actually track the calls made to certains objects and verify how the controller interracts with its results, without relying on the correctness of those objects.

This alone is enough to make mock testing a must, this allows for tests to be able to validate that the controllers read and accessed data in specific forms, which offers a great deal of freedom.

We don't want to break the tests because suddenly an error message changes its wording.

We used Mockito for mock testing.
It was an extreamly easy tool to use, and extremely powerful.
It was quite the pleasent surprise.

---
[Return to the main readme](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/README.md)
