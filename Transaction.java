import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Transaction {

  private UUID id;

  private ZonedDateTime executedOn;

  private Card card;

  public Transaction(UUID id, ZonedDateTime executedOn, Card card) {

    this.id = id;
    this.executedOn = executedOn.withZoneSameInstant(ZoneOffset.UTC);
    this.card = card;
  }

  public UUID getId() {

    return id;
  }

  public void setId(UUID id) {

    this.id = id;
  }

  public ZonedDateTime getExecutedOn() {

    return executedOn;
  }

  public void setExecutedOn(ZonedDateTime executedOn) {

    this.executedOn = executedOn.withZoneSameInstant(ZoneOffset.UTC);
  }

  public Card getCard() {

    return card;
  }
}
