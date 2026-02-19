export type EventCategory =
  | "palestra"
  | "seminario"
  | "cultural"
  | "feira"
  | "workshop"
  | "livre"
  | "conferencia"
  | "festival"
  | "outro";

export type Campus =
  | "reitoria"
  | "ondina"
  | "sao lazaro"
  | "canela"
  | "graca"
  | "federacao";

export interface Event {
  id: string;
  title: string;
  description: string;
  category: EventCategory;
  date: string;
  startTime: string;
  endTime: string;
  location: string;
  campus: Campus;
  speakers: string[];
  capacity: number;
  registered: number;
  requirements: string[];
  organizer: string;
  organizerType: string;
  image?: string;
  tags: string[];
  isRegistered?: boolean;
}

export interface EventRequestDTO {
  title: string;
  description: string;
  startTime: string; 
  endTime: string;
  categoryId: number; // Backend pede número
  locationId: number; // Backend pede número
  workloadHours: number;
  maxCapacity: number;
  onlineLink?: string;
}

export interface EventResponseDTO {
  id: number;
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  workloadHours: number;
  maxCapacity: number;
  onlineLink?: string;
  // O backend pode retornar o objeto completo ou apenas o ID/Nome
  // Ajuste conforme o JSON que seu backend retorna
  category?: { id: number; name: string } | string; 
  location?: { id: number; name: string } | string;
}