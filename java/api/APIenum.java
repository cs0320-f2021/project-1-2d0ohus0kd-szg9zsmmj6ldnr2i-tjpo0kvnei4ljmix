package api;

//This is an enum
//Basically, anything that is of type APIenum can only be one of these specific values listed below:
//This is a better alternative to passing something like an int/string if there are only
//A limited number of selections. This way there is no ambiguity about capitalization, word choice, etc.
public enum APIenum {
  RENT, REVIEW, USER
}
