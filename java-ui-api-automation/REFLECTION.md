# Reflection

## If I had two more weeks
I would add Allure reporting, environment profiles (dev/stage), and CI pipeline integration with artifact upload for reports and screenshots. I would also add more negative UI cases, API schema validation, and a combined UI + API scenario where booking data is created via API and verified in a downstream flow.

## How would you handle flaky tests in CI?
I would start by identifying the root cause using logs, screenshots, and API response bodies. Then I would improve synchronization with explicit waits, isolate test data, and run suites in a stable headless browser setup. Retries would be used only for known external instability, not as a default fix.

## Trade-offs made for the time limit
I kept the framework intentionally simple and junior-friendly: one browser, one API client, basic Cucumber HTML reporting, and no parallel execution. I focused on clear layering, reusable helpers, and readable BDD scenarios instead of advanced dependency injection or custom frameworks.
