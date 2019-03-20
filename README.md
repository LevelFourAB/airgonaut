# Airgonaut

> **airgonaut**. One who journeys through the air.

Airgonaut is a Java-library to help implement great notifications for your users.
Great notifications are hard, especially when you take into accounts things
such as multiple devices, the wish of users to receive notifications digested
or only on some devices, and localizalization of messages.

This library provides building blocks to implement notifications in your
backend and to give both you and your users control over how notifications are
handled.

Airgonaut provides:

* An interface to trigger notifications in your backend
* Multiple targets, such as e-mail and sms, with individual rendering for each target
* Planned: Support for digests, per notification target and type of notification

## Status

Unreleased, in active development for now. Maven snapshots are not yet available.

## Sending notifications

Sending notifications is done via `Notifications` and uses a data object that
is rendered to a notification.

Example:

```java
notifications.newNotification()
  .withData(new UnknownBrowserSignIn(userAgent, ip))
  .to(EmailChannel.create("security@example.com"))
  .send();
```

Notifications can be sent both to a specific receiver via a `ContactChannel`
such as `Email` or using a `NotificationReceiver` than can resolve which
channels a notification should be sent to:

```java
notifications.newNotification()
  .withData(new UserMention(...))
  .to(userNotificationReceiver)
  .send();
```

## Targets and rendering

In this library targets are in charge of delivering a notification, so targets
are things such as e-mails, SMS and push notifications. All notifications
start out as instances of `NotificationData` that are then rendered for a
specific target.

This design allows a notification to be rendered differently when sent over
say e-mail or SMS.

Example:

```java
// Data for the notification
class NewMessage implements NotificationData {
  User from;
  String plainText;
}

// Renderer for the e-mail
class NewMessageEmailRenderer implements EmailRenderer<NewMessage> {
  public void render(EmailRenderingEncounter<NewMessage> encounter) {
      NewMessage data = encounter.getData();

      encounter.setTitle("New message from " + data.getUser().getName());
      encounter.setPlainText("Message text: " + data.getPlainText());
  }
}
```

Targets always work on a `ContactChannel` and every target supports different
channels, such as the e-mail target supporting instances of `EmailChannel`.

## Setting up `Notifications`

`Notifications` is an interface and has an implementation in
`LocalNotifications`. You should create a single instance and share it
through your system.

```java
Notifications notifications = LocalNotifications.builder()
  .addRenderer(userMentionRenderer)
  .addTarget(emailTarget)
  .build();
```

You can supply a `TypeFinder` to automatically locate and create renderers:

```java
// The type finder used to locate notification renderers
TypeFinder typeFinder = TypeFinder.builder()
  .addPackage("com.example.package")
  .setInstanceFactory(instanceFactory)
  .build();

// This style of building will query the type finder for renderers
Notifications notifications = LocalNotifications.builder()
  .withTypeFinder(typeFinder)
  .addTarget(emailTarget)
  .build();
```
