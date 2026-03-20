# BI Migration Tool

An AI-powered tool that automatically migrates Tableau workbooks (.TWB, .TDS, .TWBX) to Power BI format (.PBIP, .PBIR).

## Problem It Solves
Migrating from Tableau to Power BI manually is time-consuming and error-prone. This tool automates the conversion using a multi-agent AI system powered by local LLMs (Ollama + Llama 3.1).

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring AI
- Ollama (local LLM)
- ChromaDB (vector database)
- PostgreSQL
- React (frontend)
- Docker

## Features
- Parse Tableau TWB, TDS, TWBX files
- Convert Tableau calculated fields to DAX using AI
- Generate valid Power BI PBIP/PBIR output
- Multi-agent architecture with RAG self-learning
- Real-time migration progress via WebSocket
- Side-by-side formula diff view

## Status
🚧 In Development — Sprint 0 (Environment Setup)

## How to Run
Coming soon...

## Author
Java AI Engineer — building in public