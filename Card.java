import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Card {

  private final String id;

  private CardStatus status;

  private ZonedDateTime lastUsedOn;

  public Card(String id) {

    this.lastUsedOn = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
    this.id = id;
    status = CardStatus.ACTIVE;
  }

  public Card(String id, ZonedDateTime lastUsedOn) {

    this.lastUsedOn = lastUsedOn.withZoneSameInstant(ZoneOffset.UTC);
    this.id = id;
    status = CardStatus.ACTIVE;
  }

  public ZonedDateTime getLastUsedOn() {

    return lastUsedOn;
  }

  public void setLastUsedOn(ZonedDateTime lastUsedOn) {

    this.lastUsedOn = lastUsedOn.withZoneSameInstant(ZoneOffset.UTC);
  }

  public String getId() {

    return id;
  }

  public CardStatus getStatus() {

    return status;
  }

  public void setStatus(CardStatus status) {

    this.status = status;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return id.equals(card.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
