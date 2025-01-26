# Pagination 
```java
    ResponseEntity<Page<RoleDTO>> getAllRoles(@SortDefault(sort = "priRole") @PageableDefault(size = 20) final Pageable pageable) {}
```

# order
@Order
@DependsOn

# GlobalExceptionHandling
@ControllerAdvice
@ExceptionHandler

# Async
@EnableAsync
@Async