import java.util.Collection;

public interface CardRepository {

  Collection<Card> listActiveCards();

  boolean addCard(Card card);

  boolean receiveTransaction(Transaction t);

}
