# Musify

---

Musify is an API service that makes available getting information about your favorite artist(s), along with covers for
albums.

## Summary

- [Architecture](#architecture)
    - [Design](#design)
        - [Definitions](#definitions)
    - [Persistence layer](#persistence-layer)
- [Integrations](#integrations)
    - [MusicBrainz](#musicbrainz)
    - [WikiData](#wikidata)
    - [Wikipedia](#wikipedia)
    - [Cover Art Archive](#cover-art-archive)
- [Challenges](#challenges)
- [Load testing with Grafana K6](#load-testing-with-grafana-k6)

## Architecture

Because this service requires integrations from different sources, it was decided to develop it in a feature based
approach. Doing so would help transitioning it to a microservice architecture in the future, in case high demand becomes
an issue and each source need to act in a more independent way.

### Design

The feature based approach divides the structure of this application as such:

```
|- <domain/feature>
  |- api
    |- response
      - *Response
    - <Domain/Feature>Controller
  |- client
    |- config
      - *ClientConfig
      - *ConfigProperties
    |- request
      - *Request
    |- response
      - *Response
    - *Client
  |- service
    - *Service
    - <Domain/Feature>Service
  |- repository
    - <Domain/Feature>Repository
|- config
|- shared 
```

#### Definitions

Further explanation on each level:

##### `<domain/feature>`

The name of the feature in place. e.g.: Music Artist (`musicartist`). Whereas nested packages are:

- `api` is the API layer, where feature is "exposed" to the client/user.
    - `response` API response models package.
        - `*Response` class representing the model to be used as response for an endpoint. e.g.: `MusicArtistResponse`.
    - `<Domain/Feature>Controller` class representing the API entry point for the domain/feature in this package.
      e.g.: `MusicArtistController`.
- `client` is the Client layer, where HTTP calls to an external (third-party) service are configured, if applicable.
    - `config` client configuration package
        - `*ClientConfig` configuration class of the WebClient to be used for an external (third-party) service, when
          applicable.
          e.g.: [MusicBrainzClientConfig](src/main/kotlin/code/challenge/musify/musicartist/client/config/MusicBrainzClientConfig.kt)
          .
        - `*ConfigProperties` configuration properties class.
          e.g: [MusicBrainzConfigProperties](src/main/kotlin/code/challenge/musify/musicartist/client/config/MusicBrainzConfigProperties.kt)
          .
    - `request` client request package
        - `*Request` class representing the request model for an external (third-party) service.
    - `response` client response package
        - `*Response` class representing the response model from an external (third-party) service.
          e.g.: [MusicBrainzArtistResponse](src/main/kotlin/code/challenge/musify/models/musicartists/MusicBrainzArtistResponse.kt)
          .
    - `*Client` client class making use of the configured WebClient to send HTTP requests to an external (third-party)
      service. e.g: [MusicBrainzClient](src/main/kotlin/code/challenge/musify/clients/musicartists/MusicBrainzClient.kt)
      .
- `service` is the Service layer, where desired implementation classes and contracts are established, making use of the
  Client layer, to be accessed from the API layer.
    - `*Service` implementing service class of the domain/feature in this package - may be an external (third-party)
      service provider or the actual domain/feature. e.g.: `MusicArtistService`, `MusicBrainzService`
    - `<Domain/Feature>Service` contract (interface) declaring implementation functions/methods for the domain/feature
      in this package. e.g.: `MusicArtistService`.
- `repository` is the Persistence layer, destined for data storage.
    - `model` persistence model package.
        - `<Domain/Feature>` class representing the model to be persisted to the storage.
    - `<Domain/Feature>Repository` implementation interface of the desired persistence approach.
      e.g.: `MusicArtistRepository`.

##### `config`

Application configuration files.

##### `shared`

Common files to be used throughout the project (aliases, constants, enums, etc.).

### Persistence layer

It was decided to use MongoDB to save already fetched data for a given MBID, to make calls faster and also to prevent
exceeding the rate limiting established by MusicBrainz (1 req. / sec).

Ideally, music artists data should be updated once a day, to make sure they are up-to-date.

To simplify setup, it is using MongoDB Atlas, in a sandbox environment, so it is safe to make tests. However, since it
is the lowest cluster level (M0), performance shouldn't be the best. Feel free to install MongoDB locally and change
the MongoDB Uri in the `application.yaml` file, if you want to make a better test.

> **NOTE:** The MongoDB credentials exposed in the `application.yaml` file are intentionally hard-coded to make testing
> of the application easier. It is a controlled environment, and its access may be revoked whenever pleased, or password
> changed. Ideally, it should be set using ENV Variables, but for the sake of this challenge, it is not.
> YOU SHOULD NEVER COMMIT CREDENTIALS!

## Integrations

It integrates with MusicBrainz, WikiData and Wikipedia for artists' information, and with Cover Art Archive for getting
album covers.

### MusicBrainz

[Documentation](http://musicbrainz.org/doc/Development/XML_Web_Service/Version_2)

API URL: `http://musicbrainz.org/ws/2`

### Wikidata

[Documentation](https://www.wikidata.org/wiki/Wikidata:Data_access)

API URL: `https://www.wikidata.org/wiki/Special:EntityData`

### Wikipedia

[Documentation](https://www.mediawiki.org/wiki/Special:MyLanguage/Wikimedia_REST_API)

API URL: `https://en.wikipedia.org/api/rest_v1/page/summary`

### Cover Art Archive

[Documentation](https://wiki.musicbrainz.org/Cover_Art_Archive/API)

API URL: `http://coverartarchive.org/`

## Challenges

Because this application make requests to several sources, it is crucial to have asynchronous code in place.
Despite having suspended functions in place, there are some more useful resources that could be used to make it
even better.

The Reactive libraries included in this project (files containing the word `Reactive` in it, as well as Beans marked
with `@Profile("Reactive")`) could make a huge difference, given the high load required by this application.

Unfortunately, due to lack of time and also not having used reactive programming so far, it was not possible to use it
in this project.

## Load testing with Grafana K6

There is a script file named `loadtest.js` to be used with [Grafana K6](https://k6.io/docs/getting-started/running-k6/).
It is possible to define many MBIDs and run them at once to check the application performance.

Change `vus` and `duration` value to fit the scenario you want to simulate. Visit Grafana K6 documentation to have
a better understanding of the tool.
