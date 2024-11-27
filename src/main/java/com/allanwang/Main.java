package com.allanwang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
//@ComponentScan(basePackages = "com.allanwang")
//@EnableAutoConfiguration
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age) {

    }
    // request body will be a json object
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        /// just be careful not to modify the ID of the customer. This is what doesn't change
        customerRepository.findById(id).ifPresent(oldCustomer -> {
            oldCustomer.setAge(request.age());
            oldCustomer.setEmail(request.email());
            oldCustomer.setName(request.name());
            customerRepository.save(oldCustomer);
        });
    }
//    @GetMapping("/greet")
//    public GreetResponse greet() {
//        GreetResponse response =
//                new GreetResponse(
//                    "Hello!",
//                    List.of("Java", "C++", "Swift"),
//                    new Person("Allan", 20));
//        return response;
//    }
//
//    record Person(String name, int age) {
//
//    }
//    record GreetResponse(
//            String greet,
//            List<String> favProgrammingLanguages,
//            Person person) {
//
//    }

//    class GreetResponse {
//        private final String greet;
//
//        GreetResponse(String greet) {
//            this.greet = greet;
//        }
//        public String getGreet() {
//            return greet;
//        }
//        public String toString() {
//            return "GreetResponse{" +
//                    "greet='" + greet + '\'' + '}';
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (o == null || getClass() != o.getClass()) return false;
//            GreetResponse that = (GreetResponse) o;
//            return Objects.equals(greet, that.greet);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hashCode(greet);
//        }
//    }
}
