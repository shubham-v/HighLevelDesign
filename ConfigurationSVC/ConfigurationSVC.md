# FR
- Save/Update/Remove configs for each service for each environment
- Version configs
- Save/Update/Remove secrets

# NFR
Scalable
Highly Available or Strongly Consistent

# APIs
    POST /config/{service}/{environment}
    DELETE /config/{service}/{environment}
    GET /config/{service}/{environment}?size={size}&cursor={cursor}
    GET /config/{service}/{environment}/{key}
    WATCH /config/{service}/{environment} - Subscribe to real-time updates (via WebSockets or server-sent events).
    POST /secrets
        ```json
            {
                "name": "prod-db-password",
                "value": "my_plaintext_password",  // Plaintext value (encrypted server-side)
                "algorithm": "aes-256-gcm"         // Optional: Encryption algorithm
            }
        ```
    GET /secrets/{secretId}
    GET /secrets?size={size}&cursor={cursor}
    DELETE /secrets/{secret_id}  204 No Content

# DB Schema Design
## accounts
id uuid (PK)
name varchar(64) not null unique

## roles
id uuid (pk)
role_name varchar(64) unique

## account_roles
account_id
role_id

## configurations
id uuid (PK)
key varchar(255)
value jsonb not null
service varchar(64)
environment varchar(64)
version INT Not null
UK(service, environment, key)

## configuration_versions
id uuid (PK)
config_id (FK configurations.id)
version int not null
value jsonb
UK (config_id, version)

## configuration_roles
configurations_id (FK configurations.id)
role_id (FK roles.id)

## secrets
id uuid (PK)
name varchar(128) not null
encrypted_value BYTEA not null
algorithm varchar(50) not null


# versioning

# Security
## Authentication
JWT/OAuth2 for service-to-service auth.
Authorization: Bearer <JWT_TOKEN>
401	{ "error": "Unauthorized" }

## Authorization
RBAC to restrict access (e.g., dev-team can only edit dev environment configs).
RBAC Policies:
    admin: Create/update/delete secrets.
    developer: Read metadata but cannot decrypt.
    operator: Decrypt secrets (e.g., for deployment workflows).

403 Forbidden

## Encryption
TLS for data in transit; encrypt sensitive fields at rest (e.g., AES-256).

## Audit Logs
Track configuration changes and access attempts.

# High Availability & Scalability
## Distributed Storage
Use etcd/Consul clusters or database replication.
## Stateless Servers
Deploy multiple server instances behind a load balancer.
## Auto-scaling
Scale server instances based on CPU/memory usage.
## Global Replication
Multi-region deployment for low latency and disaster recovery.

# Monitoring & Observability
## Logging
Centralized logs (e.g., ELK Stack) for API requests and errors.
Do not log plaintext secrets
## Metrics
Monitor latency, error rates, and cache performance (Prometheus/Grafana).
## Alerts
Notify on high error rates or failed health checks.


# Trade-offs
## Consistency vs. Availability
Using etcd/Consul prioritizes consistency; eventual consistency might be chosen for higher availability.

## Complexity
Introduce caching/watch mechanisms judiciously to avoid overhead.