# WebCrawler
Test task for the Intern/Junior Java Software Engineer position in Softeq
## Build
Run commands:

    gradle clean build
## Run
    docker-compose up --build
## Env
You can specify the level of parallelism based on the characteristics of your environment (number of CPUs).
Example:

    docker-compose run -e PARALLELISM=2 --service-ports  webcrawler-api
Or you can change it in the webcrawler.env file

## You can import POSTMAN requests collection

    https://www.getpostman.com/collections/4adcf98aea7feb0d00b6

## Demo video link

    https://drive.google.com/file/d/1Tlb9hfKJ4-25gpPDT2_Y3D1jnGtnF-ro/view?usp=sharing
