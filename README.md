# Guardian AI Safety System - Full Package

This archive contains:

- backend: Java Spring Boot application (detectors, sanitizers, OpenRouter client, JPA logging, Flyway)
- frontend: minimal React admin dashboard
- n8n: workflow.json to connect webhook -> backend
- CI: GitHub Actions workflow

I didn’t build a workflow this week…
I built an entire digital team inside n8n. ⚡

Meet my newest build: The Ultimate Autonomous Assistant System - A Multi-agent Engine that reads your emails, drafts replies, manages your calendar, creates content… and even thinks before acting.

All from a single Telegram message.
And yes it works while I’m asleep.

Here’s the wild part 👇

📩 Email Agent
Reads inbox → drafts replies → labels → marks → sends
(Without you touching Gmail once.)

📅 Calendar Agent
Creates events → updates → deletes → syncs
(Your schedule updates itself.)

🧠 Content Creator Agent
Researches with Tavily → writes using OpenRouter → returns ready-to-publish content.

♻️ Central Brain
Every action passes through a “decision layer” so the agent chooses the best tool just like a human assistant would.

🧵 And the whole system is triggered by ONE Telegram message.

What used to require:

- A VA
- A content writer
- An executive assistant
- A scheduler
  Now runs as a self-orchestrating AI workforce inside n8n.

This isn’t automation.
This is delegation to machines.

Built with:
🧩 n8n – brain & orchestration
🧠 OpenRouter, Inc – reasoning & writing
🌍 Tavily – real-time research
📧 Gmail API – email ops
📅 Google Calendar API – scheduling
📲 Telegram Messenger – command center

Environment
Provide secrets via environment variables (do not commit keys). See `.env.example` for development placeholders. Required vars:

- `OPENROUTER_API_KEY` — your OpenRouter API key
- `OPENROUTER_BASE_URL` — optional base URL for OpenRouter
- `OPENROUTER_MODEL` — optional model identifier
