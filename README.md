# itasks-api

## Overview
This is a dummy tasks project for interview purposes in Spring Boot

## Questions
1. **How would you handle pagination and ordering of tasks?**
* Depends on requirements, but mostly with repository pagination.

2. **What measures would you take to ensure the security of the application?**
* Depends on how exposed should be the endpoint, if not needed, would leave it only in an internal network. Would also validate all inputs and inner methods and users that access to the service.

3. **How would you scale this application if the number of users and tasks increased significantly?**
* Using load balancing for horizontal scale, optimize db queries and also clear old data, analyze and refactor logic.

5. **How would you manage database updates without downtime?**
* Using containers to deploy services.

## Disclaimers
* This is an example and intended to demonstrate to app providers a sample of how to approach an implementation. There are potentially other ways to approach it and alternatives could be considered.
* Its possible that the repo is not actively maintained.

## License
MIT

The code in this repository is covered by the included license.

## Support
Please enter an issue in the repo for any questions or problems.
