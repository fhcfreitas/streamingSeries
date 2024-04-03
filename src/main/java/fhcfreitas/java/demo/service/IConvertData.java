package fhcfreitas.java.demo.service;

public interface IConvertData {
  <T> T   obtainData(String json, Class<T> classe);
}
