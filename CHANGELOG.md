# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2020-05-05
### Added
- This changelog.
- deps.edn

### Changed
- `nano-id.custom/generate` has gone, instead of it there is a new function called `custom` which lives now in `nano-id.core` ns.
- `custom` got a new signatures: `[alphabet size]` and `[alphabet size random]`.
- Performance improvment. Some parts were re-written in Java.
- Examples in readme were updated.

### Removed 
- `nano-id.custom/generate`.

## [0.11.0] - 2020-04-29
### Added
- Benchmarking. You can run it with `lein bench`.

### Changed
- Performance improvement.


## [0.10.0] - 2019-10-09
### Added
### Changed
- Default alphabet ([#9](https://github.com/zelark/nano-id/pull/9)).

### Fixed
- Artifact size ([#8](https://github.com/zelark/nano-id/issues/8)).


## [0.9.3] - 2018-07-19
### Fixed
- CLJS implementation.

## [0.9.2] - 2018-07-06
### Added
- Custom random number generators support.

### Changed
- Performance improvement.


## [0.9.1] - 2018-06-28
### Added
- Initial implementation.
- Tests.
- CircleCI config.
