package se.l4.airgonaut.engine;

import se.l4.airgonaut.Notifications;
import se.l4.airgonaut.engine.internal.LocalNotificationsBuilderImpl;
import se.l4.commons.types.TypeFinder;

/**
 * Local version of {@link Notifications}. Created via {@link #builder()} and
 * normally uses a type finder:
 *
 * <pre>
 * // The type finder used to locate notification renderers
 * TypeFinder typeFinder = TypeFinder.builder()
 *   .addPackage("com.example.package")
 *   .setInstanceFactory(instanceFactory)
 *   .build();
 *
 * // This style of building will query the type finder for renderers
 * Notifications notifications = LocalNotifications.builder()
 *   .withTypeFinder(typeFinder)
 *   .addTarget(emailTarget)
 *   .build();
 * </pre>
 *
 * <p>
 * It's also possible to manually add renderers if use of a type finder is
 * not desirable:
 *
 * <pre>
 * Notifications notifications = LocalNotifications.builder()
 *   .addRenderer(userMentionToEmailRenderer)
 *   .addRenderers(listOfRenderers)
 *   .addTarget(emailTarget)
 *   .build();
 * </pre>
 */
public interface LocalNotifications
	extends Notifications
{

	/**
	 * Start building a new instance of {@link LocalNotifications}.
	 *
	 * @return
	 *   instance of {@link Builder}
	 */
	static Builder builder()
	{
		return new LocalNotificationsBuilderImpl();
	}

	/**
	 * Builder for instances of {@link LocalNotifications}.
	 */
	interface Builder
	{
		/**
		 * Set a type finder to use to resolve extra services.
		 *
		 * @param finder
		 *   the finder to use to locate services
		 * @return
		 *   self
		 */
		Builder withTypeFinder(TypeFinder finder);

		/**
		 * Add a renderer to this instance.
		 */
		Builder addRenderer(NotificationRenderer<?, ?> renderer);

		/**
		 * Add several renderers to this instance.
		 */
		Builder addRenderers(Iterable<NotificationRenderer<?, ?>> renderers);

		/**
		 * Add a new target to this instance.
		 *
		 * @param target
		 *   the notification target, such as an e-mail or SMS provider
		 * @return
		 *   self
		 */
		Builder addTarget(NotificationTarget<?> target);

		/**
		 * Build the instance of {@link LocalNotifications}.
		 *
		 * @return
		 *   instance of {@link LocalNotifications}
		 */
		LocalNotifications build();
	}
}
