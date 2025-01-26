# Invalidation
## Write-through Cache
data is written into the
cache and the corresponding database at the same time
### Advantage
cached data allows for fast retrieval, and since the same data gets written in the permanent storage, 
we will have complete data consistency between cache and storage
### Disadvantage
higher latency for write operations

## Write-around cache
written directly to permanent storage, bypassing the cache
### Advantage
reduce the cache being flooded with write operations
### Disadvantage
recently written data will create a “cache miss”

## Write-back cache
data is written to cache alone, and completion is immediately confirmed to the client
### Advantage
results in low latency and high throughput for write-intensive applications
### Disadvantage
speed comes with the risk of data loss in case of a crash or other adverse event because the only copy of the written data is in the cache.

# Eviction
FIFO
LIFO
LRU
MRU
LFU
RR