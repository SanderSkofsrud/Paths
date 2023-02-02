package edu.ntnu.idatt2001;

public class Passage {
  private String title;
  private String content;
  private List<Link> links;

  public Passage(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public List<Link> getLinks() {
    return links;
  }

  public boolean addLink(Link link) {
    return link != null;
  }
  public boolean setLinks(List<Link> links) {
    this.links = links;
  }

  public boolean hasLinks() {
    if (links != null) {
      return true;
    }
    return false;
  }
  @java.lang.Override
  public java.lang.String toString() {
    return "Passage{" +
            "title='" + getTitle() + '\'' +
            ", content='" + getContent() + '\'' +
            ", links=" + getLinks() +
            '}';
  }

  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Passage)) return false;
    if (!super.equals(object)) return false;

    Passage passage = (Passage) object;

    if (!getTitle().equals(passage.getTitle())) return false;

    return true;
  }

  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getTitle().hashCode();
    return result;
  }
}

