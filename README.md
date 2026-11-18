# HelpDeskJava

**HelpDeskJava** è un'applicazione backend REST sviluppata con Java e Spring Boot per la gestione delle richieste di assistenza informatica (ticket) e dei tecnici incaricati.

Il progetto permette di creare e consultare ticket, assegnarli ai tecnici, gestirne priorità e stato e consultare le richieste in base a diversi criteri.

## Funzionalità principali

### Gestione ticket
- Creazione di nuovi ticket.
- Consultazione di tutti i ticket o di un singolo ticket.
- Assegnazione di un tecnico a un ticket.
- Modifica della priorità.
- Aggiornamento controllato dello stato.
- Filtraggio dei ticket per priorità, stato e tecnico assegnato.
- Assegnazione automatica della priorità `MEDIUM` e dello stato `OPEN` alla creazione.

### Gestione tecnici
- Registrazione di nuovi tecnici.
- Consultazione di tutti i tecnici o di un singolo tecnico.
- Modifica dei dati di un tecnico.
- Controllo dell'univocità dell'indirizzo email.

### Validazione e gestione degli errori
- Validazione dei dati ricevuti tramite DTO.
- Controllo delle risorse non trovate.
- Gestione delle email duplicate.
- Gestione centralizzata delle eccezioni tramite un handler dedicato.

## Tecnologie utilizzate

- Java 21
- Spring Boot 4.0.9
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jakarta Validation
- Lombok
- Springdoc OpenAPI / Swagger UI
- Maven

## Architettura del progetto

Il progetto segue un'architettura a livelli, separando le responsabilità delle diverse componenti.

```text
src/main/java/it/fpc/helpdesk
├── controller
├── dto
├── entity
├── enums
├── exception
├── mapper
├── repository
└── service
```

- **Controller:** espongono gli endpoint REST e gestiscono le richieste HTTP.
- **DTO:** definiscono le strutture dei dati in ingresso e in uscita.
- **Entity:** rappresentano le entità persistite nel database.
- **Repository:** gestiscono l'accesso ai dati tramite Spring Data JPA.
- **Service:** contengono la logica applicativa e la gestione delle transazioni.
- **Mapper:** gestiscono la conversione tra Entity e DTO.
- **Exception:** contengono le eccezioni personalizzate e la gestione centralizzata degli errori.
- **Enums:** definiscono i valori ammessi per priorità e stato dei ticket.

## Gestione dello stato dei ticket

Il ciclo di vita di un ticket segue il seguente flusso:

```text
OPEN → IN_PROGRESS → CLOSED
```

Le transizioni consentite sono:
- `OPEN` → `IN_PROGRESS`
- `IN_PROGRESS` → `CLOSED`

Non sono consentite transizioni da `CLOSED` verso altri stati, né il passaggio diretto da `OPEN` a `CLOSED`.

Una richiesta che mantiene lo stato attuale non comporta modifiche.

## Configurazione del database

L'applicazione utilizza PostgreSQL.

Prima di avviare il progetto, creare un database chiamato:

```sql
CREATE DATABASE helpdesk;
```

Configurare le proprie credenziali PostgreSQL nel file:

`src/main/resources/application.properties`

Esempio:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/helpdesk
spring.datasource.username=IL_TUO_USERNAME
spring.datasource.password=LA_TUA_PASSWORD
```

La configurazione JPA utilizza Hibernate per aggiornare lo schema del database in base alle Entity.

## Installazione e avvio

### Requisiti
- JDK 21
- PostgreSQL
- Maven oppure Maven Wrapper

### Procedura

1. Clonare il repository:

```bash
git clone URL_DEL_REPOSITORY
cd HelpDeskJava
```

2. Creare il database PostgreSQL `helpdesk`.

3. Configurare username e password nel file `application.properties`.

4. Avviare l'applicazione.

Su Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Su Linux/macOS:

```bash
./mvnw spring-boot:run
```

L'applicazione sarà disponibile all'indirizzo:

```text
http://localhost:8080
```

## Documentazione API

La documentazione delle API è disponibile tramite Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

### Endpoint per i ticket

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | `/api/tickets` | Recupera tutti i ticket |
| POST | `/api/tickets` | Crea un nuovo ticket |
| GET | `/api/tickets/{id}` | Recupera un ticket tramite ID |
| GET | `/api/tickets/priority/{priority}` | Filtra per priorità |
| GET | `/api/tickets/status/{status}` | Filtra per stato |
| GET | `/api/tickets/priority/{priority}/status/{status}` | Filtra per priorità e stato |
| GET | `/api/tickets/technician/{technicianId}` | Recupera i ticket assegnati a un tecnico |
| PATCH | `/api/tickets/{id}/assignment` | Assegna un tecnico |
| PATCH | `/api/tickets/{id}/status` | Modifica lo stato |
| PATCH | `/api/tickets/{id}/priority` | Modifica la priorità |

### Endpoint per i tecnici

| Metodo | Endpoint | Descrizione |
|---|---|---|
| POST | `/api/technicians` | Registra un nuovo tecnico |
| GET | `/api/technicians` | Recupera tutti i tecnici |
| GET | `/api/technicians/{id}` | Recupera un tecnico tramite ID |
| PUT | `/api/technicians/{id}` | Modifica i dati di un tecnico |

### Valori disponibili

**Priorità:**
- `LOW`
- `MEDIUM`
- `HIGH`

**Stato:**
- `OPEN`
- `IN_PROGRESS`
- `CLOSED`

## Esempi di richieste

### Creazione di un tecnico

`POST /api/technicians`

```json
{
  "firstName": "Mario",
  "lastName": "Rossi",
  "email": "mario.rossi@example.com",
  "department": "Network Support"
}
```

### Creazione di un ticket

`POST /api/tickets`

```json
{
  "title": "Problema di connessione alla rete",
  "description": "La postazione dell'ufficio non riesce a collegarsi alla rete aziendale."
}
```

Alla creazione vengono assegnati automaticamente:
- Priorità: `MEDIUM`
- Stato: `OPEN`

Il ticket può essere creato senza un tecnico assegnato.

### Assegnazione di un tecnico

`PATCH /api/tickets/1/assignment`

```json
{
  "technicianId": 1
}
```

### Aggiornamento dello stato

`PATCH /api/tickets/1/status`

```json
{
  "status": "IN_PROGRESS"
}
```

### Aggiornamento della priorità

`PATCH /api/tickets/1/priority`

```json
{
  "priority": "HIGH"
}
```

## Gestione degli errori

L'applicazione utilizza una gestione centralizzata delle eccezioni per restituire risposte HTTP appropriate.

| Codice HTTP | Descrizione |
|---|---|
| 400 | Richiesta non valida o transizione di stato non consentita |
| 404 | Risorsa non trovata |
| 409 | Email del tecnico già registrata |

Gli errori di validazione dei DTO vengono gestiti centralmente tramite il `GlobalExceptionHandler`.

## Stato del progetto

Il progetto è stato sviluppato come progetto personale per mettere in pratica le principali tecnologie e i concetti dello sviluppo backend Java con Spring Boot.