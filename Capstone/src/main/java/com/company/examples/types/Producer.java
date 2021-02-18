package com.company.examples.types;


import com.company.examples.types.serde.JsonSerializer;
import com.company.examples.types.product;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class Producer {

    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:9092";
        String topic = "product";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        // create the producer
        KafkaProducer<String, product> producer = new KafkaProducer<String, product>(properties);
        product p1 = new product();
   try {

       ObjectMapper mapper = new ObjectMapper();
       InputStream inputstream = new FileInputStream(new File("product.json"));
       TypeReference<List<product>> typeReference = new TypeReference<List<product>>() { };
       List<product> prod= mapper.readValue(inputstream,typeReference);

       for(product p : prod) {



           ProducerRecord<String, product> record =
                   new ProducerRecord<String, product>(topic, p.getPogId(), p);

           // send data - asynchronous
           producer.send(record);

           // flush data
           producer.flush();
           // flush and close producer
           producer.close();

       }
   } catch (FileNotFoundException e) {
       e.printStackTrace();
   } catch (JsonParseException e) {
       e.printStackTrace();
   } catch (JsonMappingException e) {
       e.printStackTrace();
   } catch (IOException e) {
       e.printStackTrace();
   }





    }
}
