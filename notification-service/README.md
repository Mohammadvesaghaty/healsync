# Key Features:

Use cases for this service are:
Appointment Reminders: Notify patients of upcoming appointments.
Prescription Readiness Alerts: Inform patients when their prescription is ready for pickup or delivery.
Payment Confirmations: Confirm payment processing.
Emergency Notifications: Notify patients and doctors in case of emergencies.

# Channels:

Email
SMS
Push Notifications

## Core Concepts:

Event-driven architecture using a messaging system Kafka.
Template-based notifications for customization.(TODO: Still do not touch it)

## Service Architecture
NotificationController: REST endpoints for triggering notifications.
NotificationService: Core business logic for sending notifications.
KafkaProducerService: A service to handle queueing message events.
KafkaConsumerService: A service to handle consuming message events.
NotificationTemplates: Store templates for each type of notification.(TODO: Still do not touch it)
NotificationChannelService: Services for email, SMS, and push notifications.
Database: For logging and tracking notification status.(TODO: Still do not touch it) still just print out