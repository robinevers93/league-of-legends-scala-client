# Riot LOL API Client

This library uses [sttp](https://sttp.softwaremill.com/en/latest/index.html) to define the various http requests along with models and functions to read/interpret the responses. It uses ZIO to interpret the sttp requests into ZIO tasks which can then be easily composed and run by the user as needed.

## Usage

Create some config, create a `LOLService`, call a service function as needed. Service functions return a ZIO task of type `ZIO[Blocking, Throwable, Response[Either[String, A]]`. What does this type mean?

* When this task is run it will be executed on a blocking thread pool;
* When run it can fail with an unexpected Throwable exception;
* The `Response[Either[String, A]` indicates that if there is a response then it can either return a value of type `A` in the case of 2xx responses or an error message (`String`) in the case of non 2xx responses. For more details about the sttp `Response` type see https://sttp.softwaremill.com/en/latest/responses/basics.html. The `Response` contains useful data about the response like the status code.

For more details about ZIO types and values see https://zio.dev/docs/overview/overview_index.

Note the lib also provides some useful extension methods. To bring these into scope use this import `import rmlclient.syntax.LOLSyntax._`.

Hopefully the examples below are self-explanatory.

```scala
// Note type RIO[R, A] = ZIO[R, Throwable, A] 

// First configure stuff..

val config = LOLConfig(
  baseUrl = "api.riotgames.com",
  region = "euw1****"
)

val authConfig = AuthConfig(
  "key"
)

// Construct a LOL service
val service = LOLService.withConfig(config)
val serviceWithAuth = LOLService.withConfig(config).withAuth(authConfig)

// Call service functions as needed
val listMatches: RIO[Blocking, Response[Either[String, List[Match]]]] = 
  service.matchService.list(URN("user-id"))

// Import extension methods
import lolclient.syntax.LOLSyntax._

// getOrFail will return the success value or fail any non 2xx responses with a Throwable
val listMatchesOrFail: RIO[Blocking, List[Match]] = listMatches.getOrFail

// Retry 5 times, with exponential backoff, with a base duration of 1 second
val schedule = Schedule.exponential(Duration.ofSeconds(1)) && Schedule.recurs(5)

// and while the RML service says it is too busy
def retryWhile[A](response: Response[Either[String, A]]): Boolean = 
  response.code == StatusCode.TooManyRequests

// and compose the above to create a ZIO task
val getMatchesWithRetry: RIO[Blocking with Clock, Response[Either[String, List[Match]]]] = 
  listMatches.withRetry(schedule, retryWhile)

// Running stuff
val runToFuture: Future[List[Match]] = listMatchesOrFail.runToFuture
val run: List[Match] = listMatchesOrFail.unsafeRun
```
# league-of-legends-scala-client
