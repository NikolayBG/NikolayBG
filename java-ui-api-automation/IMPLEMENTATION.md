# Implementation Guide

This document explains how the UI + API test automation framework was built for the EnduroSat QA interview task.

## 1. Goal

Build a small but complete automation framework that covers:

- **UI:** https://www.saucedemo.com
- **API:** https://restful-booker.herokuapp.com

The code is intentionally written in a **junior-friendly style**: simple classes, clear names, no advanced patterns, and no unnecessary abstraction.

---

## 2. Project Structure

```
src/test/java/com/automation/
├── core/           # Framework foundation
├── pages/          # Page Object Model (UI)
├── api/            # Reusable REST client
├── steps/          # Cucumber step definitions
├── hooks/          # Browser lifecycle
├── runners/        # TestNG + Cucumber runner
└── utils/          # Shared test data and assertions

src/test/resources/
├── features/       # BDD scenarios
└── config.properties
```

### Why this structure?

Each folder has one responsibility. A junior developer can open the project and quickly understand where to add:

- a new page → `pages`
- a new API call → `api`
- a new scenario step → `steps`
- a new scenario → `features`

---

## 3. Core Layer

### `ConfigReader`

Reads values from `config.properties` and allows runtime overrides with `-Dkey=value`.

Example:
```bash
mvn test -Dheadless=true -Dui.username=standard_user
```

This keeps URLs, credentials, browser settings, and timeouts out of the test code.

### `DriverFactory`

- Creates one Chrome browser per UI scenario
- Uses `ThreadLocal` so tests stay isolated
- Supports headless mode from config
- Always quits the browser in hooks

### `BasePage`

Shared UI helpers used by all page objects:

- `click()`
- `type()`
- `getText()`
- `getElements()`
- `isDisplayed()`

All waits use **explicit waits** (`WebDriverWait`). There is no `Thread.sleep()` anywhere in the project.

### `TestContext`

Stores API data inside one scenario:

- last HTTP response
- booking id
- auth token

This avoids repeating fields and makes API steps easier to read.

---

## 4. UI Layer (Page Object Model)

Each page class owns its locators and actions.

| Page | Responsibility |
|------|----------------|
| `LoginPage` | Open app, login, read login errors |
| `InventoryPage` | Add/remove products, open cart, sort products |
| `CartPage` | Verify cart items, start checkout |
| `CheckoutPage` | Fill shipping info, finish order, read validation errors |

### Example flow

**Purchase a backpack**

1. `LoginPage.open()`
2. `LoginPage.login(...)`
3. `InventoryPage.addBackpackToCart()`
4. `InventoryPage.openCart()`
5. `CartPage.proceedToCheckout()`
6. `CheckoutPage.fillShippingDetails(...)`
7. `CheckoutPage.finishOrder()`

Step definitions only call page methods. They do not contain Selenium locators.

---

## 5. API Layer

### `BookingClient`

All Rest Assured calls live here. Step definitions never call Rest Assured directly.

Main methods:

- `authenticate(credentials)`
- `createBooking(body)`
- `getBooking(id)`
- `updateBooking(id, token, body)`
- `deleteBooking(id, token)`
- `updateBookingWithoutAuth(...)`
- `deleteBookingWithoutAuth(...)`
- `updateBookingWithToken(...)`

Private helper methods reduce duplication:

- `baseRequest()`
- `jsonRequest()`
- `authenticatedRequest(token)`

### `TestDataFactory`

Builds reusable request bodies and credentials:

- valid booking payload
- invalid booking payload
- valid credentials
- invalid credentials
- empty username credentials

### `ResponseAssertions`

Reusable API assertions:

- status code
- JSON path values
- token present / missing

---

## 6. Cucumber Scenarios

### UI (`ui.feature`) — 6 scenarios

| Scenario | Purpose |
|----------|---------|
| Successful login | Positive login |
| Invalid login outline | Data-driven negative login + locked user |
| Purchase a backpack | End-to-end checkout |
| Remove item from cart | Cart manipulation |
| Sort products by price | Extra product flow |
| Checkout validation message | Form validation |

Tags:
- `@ui`
- `@smoke` on key happy-path scenarios

### API (`api.feature`) — 5 scenarios

| Scenario | Purpose |
|----------|---------|
| Create and read booking | Basic CRUD |
| Update booking | Authenticated update |
| Delete booking | Authenticated delete |
| Non-existent booking | 404 negative case |
| Invalid booking payload | 500 negative case |

### API Security (`api-security.feature`) — 5 scenarios

These tests focus on **authentication and authorization security**:

| Scenario | Security check |
|----------|----------------|
| Invalid credentials | No token returned |
| Empty username | No token returned |
| Update without auth | Returns 403 |
| Delete without auth | Returns 403 |
| Update with invalid token | Returns 403 |

Tags:
- `@api`
- `@security`

---

## 7. Hooks and Reporting

### `Hooks`

- Starts browser before `@ui` scenarios
- Closes browser after `@ui` scenarios
- Captures screenshot on failure and attaches it to the Cucumber report

### Reports

Generated after `mvn test`:

- HTML: `target/cucumber-report.html`
- JSON: `target/cucumber-report.json`

---

## 8. How to Run

```bash
# All tests
mvn test

# UI only
mvn test -Dcucumber.filter.tags="@ui"

# API only
mvn test -Dcucumber.filter.tags="@api"

# Security tests only
mvn test -Dcucumber.filter.tags="@security"

# Smoke suite
mvn test -Dcucumber.filter.tags="@smoke"

# Headless UI
mvn test -Dheadless=true -Dcucumber.filter.tags="@ui"
```

---

## 9. Design Decisions

### Kept simple on purpose

- One browser (Chrome)
- One API client class
- No dependency injection framework
- No parallel execution
- Basic Cucumber HTML reporting

### Avoided duplication

- Page objects own UI logic
- API client owns HTTP logic
- `TestDataFactory` owns payloads
- `ResponseAssertions` owns API checks
- `BasePage` owns common Selenium waits

### Junior-friendly patterns used

- Plain Java classes
- `extends BasePage`
- Simple `if` checks and loops
- Clear method names like `addBackpackToCart()`
- Scenario steps written in business language

---

## 10. Test Coverage Summary

| Area | Count | Notes |
|------|-------|-------|
| UI scenarios | 6 | Includes data-driven login outline |
| API scenarios | 5 | CRUD + negative cases |
| Security scenarios | 5 | Auth and unauthorized access |
| **Total executed** | **18** | All passing |

---

## 11. What I Would Add Next

If I had more time, I would add:

- Allure reporting
- Environment profiles (dev/stage)
- CI pipeline with report upload
- Combined UI + API scenario
- More schema validation on API responses

These trade-offs are also described in `REFLECTION.md`.

---

## 12. Requirements Mapping

| Interview requirement | Implementation |
|-----------------------|----------------|
| Layered framework | `core`, `pages`, `api`, `steps`, `utils` |
| Page Object Model | All UI pages under `pages` |
| Reusable API client | `BookingClient` |
| External config | `config.properties` + `ConfigReader` |
| WebDriver management | `DriverFactory` + `Hooks` |
| Explicit waits | `BasePage` |
| Reporting | Cucumber HTML + JSON |
| Tags and CLI runs | `@ui`, `@api`, `@smoke`, `@security` |
| UI scenarios (4–6) | 6 scenarios |
| API scenarios (5–8) | 10 total API-related scenarios |
| Data-driven test | Invalid login `Scenario Outline` |
| Security focus | `api-security.feature` |
| README | `README.md` |
| Reflection | `REFLECTION.md` |

---

## 13. Final Result

The project is a complete, runnable automation solution that:

- follows the interview requirements
- uses simple and readable code
- avoids duplication
- includes dedicated authentication security tests
- passes all 18 scenarios with `mvn test`
