# FR
- get(key) = value
- put(key, value)
- No duplicate keys
- Insert lookup efficiently

# NFR
- Highly Scalable
- Available
- Tunable Consistent
- Highly Performant
- Low Latency
- Configurable
- Loggable
- Debuggable
- Fault Tolerent
- Secure

# CAP
- AP System

## TradeOffs
- Most Frequent data in memory else save data into disk
- Compress the data and put in memory


## Qourom Census
N = nodes
W = Write Qouram
R = Read Qouram

W + R > N string Consistency
W + R < N strong consistency not guaranteed

### Strong Consistency
Read operations see most updated data
### Weak Consistency
some read operations may not see updated value
### Eventual Consistency
latest data after some amount of time


## Versioning System
### Vector Clock
- [server, version]
  - D ([s1, v1],[s2,v2],[s3,v3]....[sn,vn])

                  D1[(Sx, 1)]
                      |Sx
                  D2[(Sx, 2)]
               Sy/           \Sz
        D3[(Sx, 2),(Sy, 1)]     D3[(Sx,2),(Sz, 1)]
                \                   /Sx
            D[(Sx, 3), (Sy, 1), (Sz, 1)]

- Drawbacks
  1. Threshold for version value
  2. Additional complexity for conflict resolution value


## Failure Detection
### Gossip Protocol
- Where each node maintain a node membership list
- it is like keeping track of hearbeat cpunter at a particular time
    MemberId | HearBeat | Time
      0 | 10232 | 12:00
      1 | 10111 | 12:01
      2 | 800   | 11:50

### SloppyQourom
- Ensures Availability
- Ignore the unavailable server for some time
- Cannot ignore the server forever

### HintedHandoff
Data has to be pushed back to the server that came back up.
#### PermanentFailure
- Merkle Tree
  - Inconsistency Detection
  - Minimal Data Transfer
  - use LCA (Least Common Ancestor) to find the difference between two nodes
  - disaster recovery
  - Data Integrity
  - Data Repair
  - Data Rebalancing
  - Data Migration
  - Data Synchronization
  - Data Versioning


# APIs
GET /api/v1/store/{key} -> value -> 200/404 {
  "message": "found/not found"
  "value": ""
}
POST /api/v1/store -> 201 {
  "message": "key store",
  "key": "",
  "value": ""
} 

PUT /api/v1/store -> 201 {
  "message": "key store",
  "key": "",
  "value": ""
} 

DELETE /api/v1/store -> 201/204 {
"message": "key store",
"key": "",
"value": ""
} 

# Node Responsibility
- Detect Failure
- Conflict Resolution
- Replicate Payload
- Failover


