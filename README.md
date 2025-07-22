# micro (µ) gallery viewer

## Architecture (classic)

The classic architecture is the one we have most commonly used up until now. The diagram below illustrates the dependencies between the
various modules.

**Important Note:** As of December 2024, we have decided to remove interfaces at the datasource level.

![Diagramme sans nom drawio](https://github.com/user-attachments/assets/54db8885-2395-495e-a4b6-7b8a56cab3a5)

The `kmp-shared` module uses all other modules for dependency injection. The arrows have been omitted to avoid overloading the diagram.

- **Android** uses the module only to expose dependency injection.
- **iOS** uses the module as the entry point for KMP.

The `domain` module automatically exposes the `data` module and the `error` module.

**Important Note:** Applications should never have access to repositories or the contents of the `local` and `remote` modules.

---

## Database

Currently, we support the choice between **Room** or no database. Room is already configured and ready to be used in your new project!

**Important Note:** If you choose to use Room, your project will not compile until you have added at least one entity.

---

## Network

Currently, we support the choice between **Ktor** or no network call management. Ktor is used via one of our libraries, which simplifies
tasks such as error handling.

---
