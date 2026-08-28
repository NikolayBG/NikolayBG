# UI + API Test Automation

## Stack
Java 17, Maven, Selenium, Cucumber, TestNG, Rest Assured.

## Prerequisites
- JDK 17+
- Maven
- Google Chrome
- Internet connection

## Run
All tests:
```bash
mvn test
```

UI only:
```bash
mvn test -Dcucumber.filter.tags="@ui"
```

API only:
```bash
mvn test -Dcucumber.filter.tags="@api"
```

Security tests only:
```bash
mvn test -Dcucumber.filter.tags="@security"
```

Smoke suite:
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

Headless UI:
```bash
mvn test -Dheadless=true -Dcucumber.filter.tags="@ui"
```

## Configuration
Edit `src/test/resources/config.properties`, or override values at runtime:
```bash
mvn test -Dui.username=standard_user -Dheadless=true
```

## Reports
After a full run (`mvn test`), reports are generated in:
- `reports/cucumber-report.html` (committed submission report)
- `reports/cucumber-report.json`

A fresh copy is also written under `target/` during the Maven build.

## Project structure
```
src/test/java/com/automation/
  core/        # config, driver, base page, shared test context
  pages/       # Page Object Model for UI
  api/         # reusable REST client
  steps/       # Cucumber step definitions
  hooks/       # browser setup, screenshots, cleanup
  runners/     # TestNG + Cucumber runner
  utils/       # test data and reusable assertions

src/test/resources/
  features/    # BDD scenarios (ui, api, api-security)
  config.properties
```

## Design decisions
- Simple classes and explicit waits (no `Thread.sleep`)
- Page objects own locators and UI actions
- API calls live in `BookingClient`, not in step definitions
- Shared API state is stored in `TestContext` per scenario
- Reusable payloads and assertions live in `utils` to avoid duplication
- Cucumber tags separate UI, API, smoke, and security suites

See `IMPLEMENTATION.md` for a detailed walkthrough of how the framework was built.
