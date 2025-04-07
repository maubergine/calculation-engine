# CALCULATION ENGINE GUIDELINES

## Build Commands
- Build: `./mvnw clean package`
- Native build: `./mvnw -Pnative clean package`
- Run tests: `./mvnw test`
- Run single test: `./mvnw test -Dtest=com.mariusrubin.calculationengine.calc.AClassName`
- Run application: `java -jar core/target/calculation-engine-core-*-shaded.jar [path/to/scenarios]`

## Code Style
- **Imports**: Java libraries first, third-party second, project imports last, alphabetical within groups
- **Naming**: camelCase for variables, UPPER_SNAKE_CASE for constants
- **Types**: BigDecimal for financial calculations with proper scale handling (2 decimals)
- **Formatting**: 2-space indentation, braces on same line as declaration
- **Error handling**: Try/catch for IO, return error codes for CLI operations
- **Testing**: Test classes prefixed with "A" (e.g., ACalculator), use AssertJ assertions

## Structure
- Interfaces in `api` package, implementations in root or specialized packages
- Calculator implementations separated by functional area
- Immutability preferred, use Java streams for collection processing
- Comprehensive JavaDoc with @author, @since, @param tags